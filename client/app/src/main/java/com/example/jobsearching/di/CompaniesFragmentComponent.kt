package com.example.jobsearching.di

import com.example.jobsearching.di.scopes.CompaniesScope
import com.example.jobsearching.fragments.CompaniesFragment
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
@CompaniesScope
interface CompaniesFragmentComponent {

    fun inject(fragment: CompaniesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): CompaniesFragmentComponent
    }
}
