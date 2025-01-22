package example.com.data.network.api

import example.com.data.mapper.toCompaniesDto
import example.com.data.mapper.toCompanyDto
import example.com.data.mapper.toResumeDto
import example.com.data.mapper.toVacancyDto
import example.com.data.network.dto.*
import example.com.data.repository.RepositoryImpl
import java.util.logging.Logger

object ApiServiceImpl : ApiService {

    private val repository = RepositoryImpl

    override suspend fun getCompanyList(): CompaniesDto {
        val companyList = repository.getCompanyList()
        return companyList.toCompaniesDto()
    }

    override suspend fun getCompany(companyId: Int?): CompanyDto? {
        val company = repository.getCompanyItem(companyId!!)
        return company.toCompanyDto()
    }

    override suspend fun getVacancyList(): VacanciesDto {
        val vacancyList = repository.getVacancyList()
        val vacancyDtoList = vacancyList.map { it.toVacancyDto() }
        return VacanciesDto(list = vacancyDtoList)
    }

    override suspend fun getVacancy(vacancyId: Int?): VacancyDto? {
        val vacancy = repository.getVacancyItem(vacancyId!!)
        return vacancy.toVacancyDto()
    }

    override suspend fun getResume(resumeId: Int): ResumeDto {
        return repository.getResume(resumeId).toResumeDto()
    }

    override suspend fun getResumes(): List<ResumeDto> {
        return repository.getResumeList().map { it.toResumeDto() }
    }

    override suspend fun updateResume(newResume: ResumeDto) {
        Logger.getLogger("ApiLog").info("Resume was updated")
        Logger.getLogger("ApiLog").warning(newResume.candidateInfo.birthDate.toString())

        repository.updateResume(newResume)
    }

    override suspend fun analyzeResume(): List<String> {
        return repository.analyzeResume()
    }

    override suspend fun getTags(resumeId: Int): Tags {
        return repository.getTags(resumeId)
    }
}