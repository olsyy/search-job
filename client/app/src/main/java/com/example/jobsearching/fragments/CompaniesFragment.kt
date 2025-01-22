package com.example.jobsearching.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.jobsearching.R
import com.example.jobsearching.activities.CompanyDetailsActivity
import com.example.jobsearching.databinding.FragmentCompaniesBinding
import com.example.jobsearching.recyclerview.CompaniesRecyclerViewAdapter
import com.example.jobsearching.utils.App
import com.example.jobsearching.utils.Utils
import com.example.jobsearching.utils.viewBinding
import com.example.jobsearching.viewmodels.CompaniesViewModel
import com.example.jobsearching.viewmodels.factory.MultiViewModelFactory
import javax.inject.Inject

class CompaniesFragment : Fragment(R.layout.fragment_companies) {

    private val binding by viewBinding(FragmentCompaniesBinding::bind)

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: CompaniesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CompaniesViewModel::class.java]
    }

    private val companiesAdapter: CompaniesRecyclerViewAdapter by lazy {
        CompaniesRecyclerViewAdapter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent
            .companiesFragmentComponent()
            .create()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        viewModel.loadCompaniesList()
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerViewCompanies
        recyclerView.adapter = companiesAdapter
        companiesAdapter.onCompanyItemClickListener =
            object : CompaniesRecyclerViewAdapter.OnCompanyItemClickListener {
                override fun onCompanyItemClick(companyId: Int) {
                    CompanyDetailsActivity.start(requireContext(), companyId)
                }
            }
    }

    private fun observeViewModel() {
        viewModel.companiesList.observe(viewLifecycleOwner) {
            companiesAdapter.companiesList = it.list
        }
        viewModel.error.observe(viewLifecycleOwner) { Utils.showError(requireContext(), it) }
    }

    companion object {

        @JvmStatic
        fun newInstance() = CompaniesFragment()
    }
}