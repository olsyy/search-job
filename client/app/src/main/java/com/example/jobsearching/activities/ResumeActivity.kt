package com.example.jobsearching.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import com.example.domain.entity.CandidateInfoItem
import com.example.domain.entity.ContactsItem
import com.example.domain.entity.EducationItem
import com.example.domain.entity.JobExperienceItem
import com.example.domain.entity.ResumeItem
import com.example.jobsearching.R
import com.example.jobsearching.databinding.ActivityResumeBinding
import com.example.jobsearching.utils.App
import com.example.jobsearching.utils.Utils
import com.example.jobsearching.utils.viewBinding
import com.example.jobsearching.viewmodels.ResumeViewModel
import com.example.jobsearching.viewmodels.factory.MultiViewModelFactory
import javax.inject.Inject

class ResumeActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityResumeBinding::inflate)

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: ResumeViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        inject()
        initializeViewModel()
        setupToolbar()
        setClickListenerItemAdd()
        observeViewModel()
    }

    private fun initializeViewModel() {
        viewModel.apply {
            isEditDialogShown = false
            getResumeFromRoom()
            loadResume()
        }
    }

    override fun onStop() {
        when {
            viewModel.isEditDialogShown && isChangingConfigurations -> {
                viewModel.changeConfiguration()
            }

            viewModel.isEditMode.value == true -> {
                viewModel.saveResume(getNewResume())
            }

            else -> {
                viewModel.stopEditing()
            }
        }
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun inject() {
        (application as App).appComponent
            .resumeActivity()
            .create()
            .inject(this)
    }


    private fun observeViewModel() {
        viewModel.resumeItem.observe(this) {
            it?.let { updateUi(it) }
        }
        viewModel.tags.observe(this) {
            updateTags(it.toString())
        }
        viewModel.error.observe(this) {
            viewModel.resumeItem.value?.let { updateUi(it) }
            Utils.showError(this@ResumeActivity, it)
        }
        viewModel.isEditMode.observe(this) {
            if (it == true) {
                enableEditMode()
            } else {
                enableViewMode()
            }
        }
        viewModel.shouldShowEditDialog.observe(this) {
            if (it == true && !viewModel.isEditDialogShown) {
                showAlertDialogToContinueEdit()
            }
        }
    }

    private fun showAlertDialogToContinueEdit() {
        viewModel.isEditDialogShown = true
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_resume_editing_title))
            .setPositiveButton(getString(R.string.dialog_positive_button)) { _, _ ->
                viewModel.resumeItemRoom?.let { updateUi(it) }
                viewModel.startEditing()
                viewModel.isEditDialogShown = false
            }
            .setNegativeButton(getString(R.string.dialog_negative_button)) { _, _ ->
                viewModel.stopEditing()
                viewModel.isEditDialogShown = false
            }
            .create()
            .show()
    }

    private fun setClickListenerItemAdd() {
        binding.imageButtonSaveEducation.setOnClickListener {
            val educationContainer = binding.linearLayoutEducation
            val newEducationView = inflateAndPopulateView(
                R.layout.item_education,
                educationContainer
            )
            newEducationView.setGroupVisibility(R.id.clViewGroupEdu, R.id.clEditGroupEdu)
            educationContainer.addView(newEducationView)
            newEducationView.findViewById<ImageButton>(R.id.imageButtonDeleteEducation)
                .setOnClickListener {
                    val currentView = it.parent as View
                    currentView.visibility = View.GONE
                }
        }
        binding.imageButtonSaveJob.setOnClickListener {
            val jobsContainer = binding.linearLayoutJobExperience
            val newJobView = inflateAndPopulateView(
                R.layout.item_education,
                jobsContainer
            )
            newJobView.setGroupVisibility(R.id.clViewGroupEdu, R.id.clEditGroupEdu)
            jobsContainer.addView(newJobView)
            newJobView.findViewById<ImageButton>(R.id.imageButtonDeleteEducation)
                .setOnClickListener {
                    val currentView = it.parent as View
                    currentView.visibility = View.GONE
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.resume_menu, menu)
        return true
    }

    private fun setupToolbar() {
        val toolbar = binding.toolBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.inflateMenu(R.menu.resume_menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (viewModel.isEditMode.value == true) {
                    showAlertDialogToSaveChangesLocally()
                } else {
                    onBackPressedDispatcher.onBackPressed()
                }
                true
            }

            R.id.actionEdit -> {
                viewModel.resumeItem.value?.let { updateUi(it) }
                viewModel.startEditing()
                invalidateOptionsMenu()
                true
            }

            R.id.actionUndo -> {
                val isEdited = viewModel.isEditedResume(getNewResume())
                if (isEdited) {
                    showAlertDialogToSaveResume()
                } else {
                    viewModel.stopEditing()
                }
                true
            }

            R.id.actionSave -> {
                viewModel.updateResume(getNewResume())
                viewModel.stopEditing()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showAlertDialogToSaveChangesLocally() = AlertDialog.Builder(this)
        .setTitle(getString(R.string.dialog_to_save_changes_locally))
        .setPositiveButton(getString(R.string.dialog_positive_button)) { _, _ ->
            onBackPressedDispatcher.onBackPressed()
        }
        .setNegativeButton(getString(R.string.dialog_negative_button)) { _, _ ->
            viewModel.stopEditing()
            onBackPressedDispatcher.onBackPressed()
        }
        .setNeutralButton(getString(R.string.dialog_neutral_button)) { dialog, _ ->
            dialog.dismiss()
        }
        .create()
        .show()

    private fun showAlertDialogToSaveResume() = AlertDialog.Builder(this)
        .setTitle(getString(R.string.dialog_save_resume_title))
        .setPositiveButton(getString(R.string.dialog_positive_button)) { _, _ ->
            viewModel.updateResume(getNewResume())
        }
        .setNegativeButton(getString(R.string.dialog_negative_button)) { _, _ ->
            viewModel.stopEditing()
            viewModel.resumeItem.value?.let { updateUi(it) }
        }
        .setNeutralButton(getString(R.string.dialog_neutral_button)) { dialog, _ ->
            dialog.dismiss()
        }
        .create()
        .show()

    private fun enableViewMode() {
        viewModel.switchVisibility = false
        binding.constrainLayoutMain.setGroupVisibility(
            binding.clViewGroup.id,
            binding.clEditGroup.id
        )
        binding.linearLayoutEducation.forEach { childView ->
            childView.visibility = View.VISIBLE
            childView.setGroupVisibility(R.id.clViewGroupEdu, R.id.clEditGroupEdu)
        }
        binding.linearLayoutJobExperience.forEach { childView ->
            childView.visibility = View.VISIBLE
            childView.setGroupVisibility(R.id.clViewGroupEdu, R.id.clEditGroupEdu)
        }
        invalidateOptionsMenu()
    }

    private fun enableEditMode() {
        viewModel.switchVisibility = true
        binding.constrainLayoutMain.setGroupVisibility(
            binding.clViewGroup.id,
            binding.clEditGroup.id
        )
        binding.linearLayoutEducation.forEach { showEducationItem(it) }
        setupDeleteButton(binding.linearLayoutEducation, R.id.imageButtonDeleteEducation)

        binding.linearLayoutJobExperience.forEach { showJobExperienceItem(it) }
        setupDeleteButton(binding.linearLayoutJobExperience, R.id.imageButtonDeleteJob)
        invalidateOptionsMenu()
    }

    private fun showEducationItem(view: View) {
        view.setGroupVisibility(R.id.clViewGroupEdu, R.id.clEditGroupEdu)
        view.transferTextToEditText(R.id.textViewTypeItem, R.id.editTextTypeEduItem)
        view.transferTextToEditText(R.id.textViewStartYearItem, R.id.editTextYearStartItem)
        view.transferTextToEditText(R.id.textViewEndYearItem, R.id.editTextYearEndItem)
        view.transferTextToEditText(
            R.id.textViewEducationDescriptionItem,
            R.id.editTextEducationDescriptionItem
        )
    }

    private fun showJobExperienceItem(view: View) {
        view.setGroupVisibility(R.id.clViewGroupEdu, R.id.clEditGroupEdu)
        view.transferTextToEditText(
            R.id.textViewCompanyNameJobExperience,
            R.id.editTextCompanyNameJobExperience
        )
        view.transferTextToEditText(R.id.textViewStartYearJob, R.id.editTextYearStartJob)
        view.transferTextToEditText(R.id.textViewEndYearJob, R.id.editTextYearEndJob)
        view.transferTextToEditText(
            R.id.textViewEducationDescriptionJob,
            R.id.editTextEducationDescriptionJob
        )
    }

    private fun View.setGroupVisibility(
        viewGroup: Int,
        editGroup: Int
    ) {
        val editGroupVisibility = if (viewModel.switchVisibility) View.VISIBLE else View.GONE
        val viewGroupVisibility = if (viewModel.switchVisibility) View.GONE else View.VISIBLE
        findViewById<Group>(viewGroup).visibility = viewGroupVisibility
        findViewById<Group>(editGroup).visibility = editGroupVisibility
    }

    private fun setupDeleteButton(viewGroup: ViewGroup, buttonId: Int) {
        viewGroup.forEach { childView ->
            childView.findViewById<ImageButton>(buttonId).setOnClickListener {
                (it.parent as View).visibility = View.GONE
            }
        }
    }

    private fun View.transferTextToEditText(textViewId: Int, editTextId: Int) {
        val text = findViewById<TextView>(textViewId)?.text
        val editText = findViewById<EditText>(editTextId)
        if (editText != null && text != null) {
            editText.setText(text)
        }
    }

    private fun getNewResume(): ResumeItem {
        val educationList = getNewEducationList()
        val jobExperienceList = getNewJobExperienceList()
        val candidateInfo = getNewCandidateInfo()
        val newResume = ResumeItem(
            candidateInfo = candidateInfo,
            education = educationList,
            jobExperience = jobExperienceList,
            freeForm = binding.editTextFreeForm.text.toString(),
            tags = emptyList()
        )
        return newResume
    }

    private fun getNewEducationList(): List<EducationItem> {
        val educationList = mutableListOf<EducationItem>()
        binding.linearLayoutEducation.forEachIndexed { index, view ->
            if (view.visibility == View.VISIBLE) {
                val type = view.getTextFromEditText(R.id.editTextTypeEduItem)
                val startYear = view.getTextFromEditText(R.id.editTextYearStartItem)
                val endYear = view.getTextFromEditText(R.id.editTextYearEndItem)
                val description = view.getTextFromEditText(R.id.editTextEducationDescriptionItem)

                if (isViewTextNotBlank(type, startYear, endYear, description)) {
                    val educationItem = EducationItem(
                        type = type.lowercase(),
                        yearStart = startYear,
                        yearEnd = endYear,
                        description = description
                    )
                    if (!educationList.contains(educationItem)) educationList.add(educationItem)
                }
            }
        }
        return educationList
    }

    private fun getNewJobExperienceList(): List<JobExperienceItem> {
        val jobExperienceList = mutableListOf<JobExperienceItem>()
        binding.linearLayoutJobExperience.forEachIndexed { _, view ->
            if (view.visibility == View.VISIBLE) {
                val companyName = view.getTextFromEditText(R.id.editTextCompanyNameJobExperience)
                val startYear = view.getTextFromEditText(R.id.editTextYearStartJob)
                val endYear = view.getTextFromEditText(R.id.editTextYearEndJob)
                val description = view.getTextFromEditText(R.id.editTextEducationDescriptionJob)
                if (isViewTextNotBlank(companyName, startYear, endYear, description)) {
                    val jobItem = JobExperienceItem(
                        companyName = companyName,
                        dateStart = startYear,
                        dateEnd = endYear,
                        description = description
                    )
                    if (!jobExperienceList.contains(jobItem)) jobExperienceList.add(jobItem)
                }
            }
        }
        return jobExperienceList
    }

    private fun getNewCandidateInfo(): CandidateInfoItem {
        with(binding) {
            return CandidateInfoItem(
                name = editTextName.text.toString(),
                profession = editTextProfession.text.toString(),
                sex = getNewGender(radioGroupGender.checkedRadioButtonId),
                birthDate = editTextBirthDate.text.toString(),
                contacts = ContactsItem(
                    phone = editTextPhone.text.toString(),
                    email = editTextEmail.text.toString()
                ),
                relocation = checkBoxRelocation.isChecked
            )
        }
    }

    private fun isViewTextNotBlank(vararg viewText: String): Boolean {
        viewText.forEach { if (it.isBlank()) return false }
        return true
    }

    private fun View.getTextFromEditText(view: Int) =
        findViewById<EditText>(view)?.text?.toString() ?: ""

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.actionEdit)?.isVisible = !viewModel.switchVisibility
        menu?.findItem(R.id.actionSave)?.isVisible = viewModel.switchVisibility
        menu?.findItem(R.id.actionUndo)?.isVisible = viewModel.switchVisibility
        return super.onPrepareOptionsMenu(menu)
    }

    private fun updateUi(resumeItem: ResumeItem) {
        Log.d(TAG, "UPDATE RESUME : $resumeItem")
        updateCandidateInfoViewState(resumeItem.candidateInfo)
        updateContactsViewState(resumeItem.candidateInfo.contacts)
        updateFreeFormViewState(resumeItem.freeForm)
        updateJobExperience(resumeItem.jobExperience)
        updateEducation(resumeItem.education)
    }

    private fun updateCandidateInfoViewState(info: CandidateInfoItem) {
        val drawable = ContextCompat.getDrawable(this, getGenderIcon(info.sex))
        val radioButtonId = getRadioButtonId(info.sex)
        with(binding) {
            textViewName.text = info.name
            textViewProfession.text = info.profession
            textViewBirthDate.text = info.birthDate
            textViewRelocation.text =
                getString(R.string.relocation_input, info.relocation.toString())

            editTextName.setText(info.name)
            editTextProfession.setText(info.profession)
            radioGroupGender.check(radioButtonId)
            imageViewGender.setImageDrawable(drawable)
            editTextBirthDate.setText(info.birthDate)
            checkBoxRelocation.isChecked = info.relocation
        }
    }

    private fun getRadioButtonId(gender: String) = when (gender) {
        FEMALE_GENDER -> R.id.femaleRadioButton
        else -> R.id.maleRadioButton
    }

    private fun getNewGender(buttonId: Int) = when (buttonId) {
        R.id.femaleRadioButton -> FEMALE_GENDER
        else -> MALE_GENDER
    }

    private fun updateFreeFormViewState(freeForm: String) {
        with(binding) {
            textViewFreeForm.text = freeForm
            editTextFreeForm.setText(freeForm)
        }
    }

    private fun updateTags(tags: String) {
        val newStr = getString(
            R.string.tags, tags
                .lowercase()
                .removeSurrounding("[", "]")
                .trimEnd(',', ' ')
        )
        binding.textViewTags.text = newStr
    }

    private fun updateContactsViewState(contacts: ContactsItem) {
        with(binding) {
            textViewPhone.text = contacts.phone
            textViewEmail.text = contacts.email

            editTextPhone.setText(contacts.phone)
            editTextEmail.setText(contacts.email)
        }
    }

    private fun updateEducation(educationList: List<EducationItem>) {
        val educationContainer = binding.linearLayoutEducation
        educationContainer.removeAllViews()
        educationList.onEach { item ->
            val educationView = inflateAndPopulateView(
                R.layout.item_education,
                educationContainer
            ) {
                findViewById<TextView>(R.id.textViewTypeItem).text =
                    item.type.replaceFirstChar { it.uppercase() }
                findViewById<TextView>(R.id.textViewStartYearItem).text = item.yearStart
                findViewById<TextView>(R.id.textViewEndYearItem).text = item.yearEnd
                findViewById<TextView>(R.id.textViewEducationDescriptionItem).text =
                    item.description
            }
            educationContainer.addView(educationView)
        }
    }

    private fun updateJobExperience(jobsList: List<JobExperienceItem>) {
        val jobsContainer = binding.linearLayoutJobExperience
        jobsContainer.removeAllViews()
        jobsList.onEach { item ->
            val jobView = inflateAndPopulateView(
                R.layout.item_job_experience,
                jobsContainer
            ) {
                findViewById<TextView>(R.id.textViewCompanyNameJobExperience).text =
                    item.companyName
                findViewById<TextView>(R.id.textViewStartYearJob).text = item.dateStart
                findViewById<TextView>(R.id.textViewEndYearJob).text = item.dateEnd
                findViewById<TextView>(R.id.textViewEducationDescriptionJob).text =
                    item.description
            }
            jobsContainer.addView(jobView)
        }
    }

    private fun inflateAndPopulateView(
        layoutId: Int,
        container: ViewGroup,
        populate: View.() -> Unit = {}
    ): View {
        return layoutInflater.inflate(layoutId, container, false).apply(populate)
    }

    private fun getGenderIcon(sex: String) = when (sex) {
        FEMALE_GENDER -> R.drawable.ic_gender_woman
        else -> R.drawable.ic_gender_man
    }

    companion object {

        const val TAG = "ResumeActivityS"

        private const val FEMALE_GENDER = "female"
        private const val MALE_GENDER = "male"

        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, ResumeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }
}