package com.example.jobsearching.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.domain.entity.VacancyItem
import com.example.jobsearching.R
import com.example.jobsearching.activities.VacancyDetailActivity
import com.example.jobsearching.databinding.FragmentVacanciesBinding
import com.example.jobsearching.recyclerview.VacanciesRecyclerViewAdapter
import com.example.jobsearching.utils.App
import com.example.jobsearching.utils.Utils
import com.example.jobsearching.utils.viewBinding
import com.example.jobsearching.viewmodels.VacanciesViewModel
import com.example.jobsearching.viewmodels.factory.MultiViewModelFactory
import javax.inject.Inject


class VacanciesFragment : Fragment(R.layout.fragment_vacancies) {

    private val binding by viewBinding(FragmentVacanciesBinding::bind)

    private val vacanciesAdapter: VacanciesRecyclerViewAdapter by lazy {
        VacanciesRecyclerViewAdapter()
    }

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: VacanciesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[VacanciesViewModel::class.java]
    }

    private val filter by lazy {
        arguments?.getInt(FILTER)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        viewModel.loadVacanciesList(filter)
        observeViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent
            .vacanciesFragmentComponent()
            .create()
            .inject(this)
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerViewCompanies
        recyclerView.adapter = vacanciesAdapter
        vacanciesAdapter.onVacancyItemClickListener =
            object : VacanciesRecyclerViewAdapter.OnVacancyItemClickListener {
                override fun onVacancyItemClick(vacancy: VacancyItem) {
                    val vacancyId = viewModel.getVacancyId(vacancy)
                    Log.d("VacancyIdS", "Vacancy: $vacancyId")
                    VacancyDetailActivity.start(requireContext(), vacancyId)
                }
            }
    }

    private fun observeViewModel() {
        viewModel.vacanciesList.observe(viewLifecycleOwner) {
            vacanciesAdapter.vacanciesList = it.list
        }
        viewModel.error.observe(viewLifecycleOwner) { Utils.showError(requireContext(), it) }
    }

    companion object {

        private const val FILTER = "filter"

        @JvmStatic
        fun newInstance() = VacanciesFragment()

        @JvmStatic
        fun newInstance(companyId: Int) = VacanciesFragment().apply {
            arguments = Bundle().apply {
                putInt(FILTER, companyId)
            }
        }
    }
}