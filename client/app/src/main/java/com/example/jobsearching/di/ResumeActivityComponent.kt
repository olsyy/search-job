package com.example.jobsearching.di

import com.example.jobsearching.activities.ResumeActivity
import com.example.jobsearching.di.scopes.CompaniesScope
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
@CompaniesScope
interface ResumeActivityComponent {

    fun inject(activity: ResumeActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ResumeActivityComponent
    }
}