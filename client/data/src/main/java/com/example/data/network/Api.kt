package com.example.data.network

import com.example.data.network.dto.CompaniesListDto
import com.example.data.network.dto.CompanyDto
import com.example.data.network.dto.ResumeDto
import com.example.data.network.dto.VacanciesListDto
import com.example.data.network.dto.VacancyDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @GET("/companies")
    suspend fun fetchCompaniesList(): Response<CompaniesListDto>

    @GET("/companies/{companyId}")
    suspend fun fetchCompanyDetails(@Query("companyId") companyId: Int): Response<CompanyDto>

    @GET("/vacancies")
    suspend fun fetchVacanciesList(): Response<VacanciesListDto>

    @GET("/vacancies/{vacancyId}")
    suspend fun fetchVacancyDetails(@Query("vacancyId") vacancyId: Int): Response<VacancyDto>

    @GET("/resumes/my")
    suspend fun fetchResume(): Response<ResumeDto>

    @POST("/resumes/update/1")
    suspend fun updateResume(@Body newResume: ResumeDto): List<String>
}