package com.example.jobsearching.di

import com.example.jobsearching.activities.VacancyDetailActivity
import com.example.jobsearching.di.scopes.CompaniesScope
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
@CompaniesScope
interface VacancyDetailsActivityComponent {

    fun inject(activity: VacancyDetailActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): VacancyDetailsActivityComponent
    }
}