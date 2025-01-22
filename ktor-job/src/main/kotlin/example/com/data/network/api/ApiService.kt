package example.com.data.network.api

import example.com.data.network.dto.*

interface ApiService {
    suspend fun getCompanyList(): CompaniesDto
    suspend fun getCompany(companyId: Int?): CompanyDto?
    suspend fun getVacancyList(): VacanciesDto
    suspend fun getVacancy(vacancyId: Int?): VacancyDto?
    suspend fun getResume(resumeId: Int): ResumeDto
    suspend fun getResumes(): List<ResumeDto>
    suspend fun updateResume(newResume: ResumeDto)
    suspend fun analyzeResume(): List<String>
    suspend fun getTags(resumeId: Int): Tags
}