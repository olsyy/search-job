package com.example.jobsearching.di


import androidx.lifecycle.ViewModel
import com.example.jobsearching.viewmodels.CompaniesViewModel
import com.example.jobsearching.viewmodels.CompanyDetailViewModel
import com.example.jobsearching.viewmodels.ResumeViewModel
import com.example.jobsearching.viewmodels.VacanciesViewModel
import com.example.jobsearching.viewmodels.VacancyDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

@Module
interface ViewModelModule {

    @IntoMap
    @StringKey("CompaniesViewModel")
    @Binds
    fun bindCompaniesViewModel(impl: CompaniesViewModel): ViewModel

    @IntoMap
    @StringKey("VacanciesViewModel")
    @Binds
    fun bindVacanciesViewModel(impl: VacanciesViewModel): ViewModel

    @IntoMap
    @StringKey("CompanyDetailViewModel")
    @Binds
    fun bindCompanyDetailViewModel(impl: CompanyDetailViewModel): ViewModel

    @IntoMap
    @StringKey("VacancyDetailViewModel")
    @Binds
    fun bindVacancyDetailViewModel(impl: VacancyDetailViewModel): ViewModel

    @IntoMap
    @StringKey("ResumeViewModel")
    @Binds
    fun bindVacancyResumeViewModel(impl: ResumeViewModel): ViewModel
}