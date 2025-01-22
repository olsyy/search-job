package com.example.jobsearching.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.domain.entity.CompanyItem
import com.example.jobsearching.R
import com.example.jobsearching.databinding.ActivityCompanyDetailsBinding
import com.example.jobsearching.fragments.VacanciesFragment
import com.example.jobsearching.utils.App
import com.example.jobsearching.utils.Utils
import com.example.jobsearching.utils.viewBinding
import com.example.jobsearching.viewmodels.CompanyDetailViewModel
import com.example.jobsearching.viewmodels.factory.MultiViewModelFactory
import javax.inject.Inject

class CompanyDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: CompanyDetailViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(ActivityCompanyDetailsBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).appComponent
            .companyDetailsActivity()
            .create()
            .inject(this)

        setContentView(binding.root)

        setupToolbar()

        val companyId = intent.getIntExtra(EXTRA_COMPANY_ID, 0)
        viewModel.loadCompanyDetail(companyId)
        observeViewModel()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentVacanciesList, VacanciesFragment.newInstance(companyId))
                .commit()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun observeViewModel() {
        viewModel.companyItem.observe(this) { item -> updateUi(item) }
        viewModel.error.observe(this) { Utils.showError(this@CompanyDetailsActivity, it) }
    }

    private fun updateUi(item: CompanyItem) {
        binding.textViewCompanyName.text = getString(R.string.company_name_input, item.name)
        binding.textViewActivityField.text =
            getString(R.string.activity_field_input, item.activityField)
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

        private const val EXTRA_COMPANY_ID = "extra_company_item"

        @JvmStatic
        fun start(context: Context, companyId: Int) {
            val intent = Intent(context, CompanyDetailsActivity::class.java).apply {
                putExtra(EXTRA_COMPANY_ID, companyId)
            }
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }
}