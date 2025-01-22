package com.example.jobsearching.di

import com.example.jobsearching.di.scopes.CompaniesScope
import com.example.jobsearching.fragments.VacanciesFragment
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
@CompaniesScope
interface VacanciesFragmentComponent {

    fun inject(fragment: VacanciesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): VacanciesFragmentComponent
    }
}
