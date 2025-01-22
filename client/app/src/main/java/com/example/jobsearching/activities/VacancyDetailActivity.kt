package com.example.jobsearching.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.entity.VacancyItem
import com.example.jobsearching.R
import com.example.jobsearching.databinding.ActivityVacancyDetailBinding
import com.example.jobsearching.utils.App
import com.example.jobsearching.utils.Utils
import com.example.jobsearching.utils.viewBinding
import com.example.jobsearching.viewmodels.factory.MultiViewModelFactory
import com.example.jobsearching.viewmodels.VacancyDetailViewModel
import javax.inject.Inject

class VacancyDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: VacancyDetailViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(ActivityVacancyDetailBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacancy_detail)

        (application as App).appComponent
            .vacancyDetailsActivity()
            .create()
            .inject(this)

        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val vacancyId = intent.getIntExtra(EXTRA_VACANCY_ID, 0)
        viewModel.loadVacancyDetail(vacancyId)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.error.observe(this) { Utils.showError(this@VacancyDetailActivity, it) }
        viewModel.vacancyItem.observe(this) { item -> updateUi(item) }
    }

    private fun updateUi(item: VacancyItem) {
        viewModel.loadCompanyId(item.companyName)
        val companyNameSpannable = createCompanyNameSpannable(item.companyName)

        with(binding) {
            textViewVacancyTitle.text = getString(R.string.vacancy_input, item.profession)
            textViewCompanyName.apply {
                text = companyNameSpannable
                movementMethod = LinkMovementMethod.getInstance()
                highlightColor = Color.TRANSPARENT
            }
            textViewCandidateLevel.text =
                getString(R.string.candidate_level_input, item.level)
            textViewSalaryLevel.text = getString(R.string.salary_input, item.salary)
        }
    }

    private fun createCompanyNameSpannable(companyName: String): SpannableString {
        return SpannableString("Company name: $companyName").apply {
            setSpan(object : ClickableSpan() {
                override fun onClick(view: View) {
                    viewModel.companyId?.let {
                        CompanyDetailsActivity.start(this@VacancyDetailActivity, it)
                    }
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = Color.BLUE
                    ds.isUnderlineText = false
                }
            }, length - companyName.length, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        private const val EXTRA_VACANCY_ID = "extra_company_item"

        @JvmStatic
        fun start(context: Context, vacancyId: Int) {
            val intent = Intent(context, VacancyDetailActivity::class.java).apply {
                putExtra(EXTRA_VACANCY_ID, vacancyId)
            }
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }
}