package com.example.jobsearching.di

import android.app.Application
import com.example.data.di.NetworkModule
import com.example.data.di.RepositoryModule
import com.example.data.di.RoomModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        RoomModule::class
    ]
)
@Singleton
interface AppComponent {

    fun companiesFragmentComponent(): CompaniesFragmentComponent.Factory
    fun vacanciesFragmentComponent(): VacanciesFragmentComponent.Factory
    fun companyDetailsActivity(): CompanyDetailsActivityComponent.Factory
    fun vacancyDetailsActivity(): VacancyDetailsActivityComponent.Factory
    fun resumeActivity(): ResumeActivityComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): AppComponent
    }
}