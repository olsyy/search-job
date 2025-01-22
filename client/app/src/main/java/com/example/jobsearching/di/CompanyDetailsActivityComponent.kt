package com.example.jobsearching.di

import com.example.jobsearching.activities.CompanyDetailsActivity
import com.example.jobsearching.di.scopes.CompaniesScope
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
@CompaniesScope
interface CompanyDetailsActivityComponent {

    fun inject(activity: CompanyDetailsActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): CompanyDetailsActivityComponent
    }
}