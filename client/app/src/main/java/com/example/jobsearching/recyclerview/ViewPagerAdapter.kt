package com.example.jobsearching.recyclerview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.jobsearching.fragments.CompaniesFragment
import com.example.jobsearching.fragments.VacanciesFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = NUM_TABS

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CompaniesFragment.newInstance()
            1 -> VacanciesFragment.newInstance()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }

    companion object {

        private const val NUM_TABS = 2
    }
}