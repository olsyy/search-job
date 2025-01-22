package example.com.domain.repository

import example.com.data.network.dto.ResumeDto
import example.com.data.network.dto.Tags
import example.com.domain.entity.*

interface Repository {

    fun getCompanyList(): List<CompanyItem>

    fun getCompanyItem(companyId: Int): CompanyItem

    fun getVacanciesForCompany(companyId: Int): List<VacancyItem>

    fun getVacancyList(): List<VacancyItem>

    fun getVacancyItem(vacancyId: Int): VacancyItem

    fun getCompanyNameByVacancyId(vacancyId: Int): String

    fun getResumeList(): List<ResumeItem>

    fun getResume(resumeId: Int): ResumeItem

    fun insertResume(resume: ResumeItem): Unit

    fun getEducationForResume(resumeId: Int): List<Education>

    fun getJobExperienceForResume(resumeId: Int): List<JobExperience>

    fun updateResume(newResume: ResumeDto)

    fun analyzeResume(): List<String>

    fun getTags(resumeId: Int): Tags
}