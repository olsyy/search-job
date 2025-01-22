package com.example.domain.repository

import com.example.domain.entity.CompaniesList
import com.example.domain.entity.CompanyItem
import com.example.domain.entity.ResumeItem
import com.example.domain.entity.VacanciesList
import com.example.domain.entity.VacancyItem


interface Repository {

    suspend fun getCompaniesList(): Result<CompaniesList>

    suspend fun getCompanyDetail(companyId: Int): Result<CompanyItem>

    suspend fun getVacanciesList(): Result<VacanciesList>

    suspend fun getVacancyDetail(vacancyId: Int): Result<VacancyItem>

    suspend fun getCompanyName(companyId: Int): Result<String>

    suspend fun getCompanyId(companyName: String): Result<Int>

    suspend fun getResume() : Result<ResumeItem>

    suspend fun updateResume(newResume: ResumeItem) : List<String>

    suspend fun saveResumeLocally(resumeItem: ResumeItem)

    suspend fun getResumeLocally(): ResumeItem?

    suspend fun deleteResumeLocally()
}