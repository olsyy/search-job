package example.com.data.repository

import example.com.data.db.*
import example.com.data.mapper.*
import example.com.data.network.dto.ResumeDto
import example.com.data.network.dto.Tags
import example.com.domain.entity.*
import example.com.domain.repository.Repository
import jdk.internal.net.http.common.Log
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.slf4j.LoggerFactory
import java.time.temporal.ChronoUnit

object RepositoryImpl : Repository {

    private val logger = LoggerFactory.getLogger("RepositoryLog")
    override fun getCompanyList(): List<CompanyItem> = transaction {
        CompaniesTable
            .selectAll()
            .toList()
            .toCompaniesList()
    }

    override fun getCompanyItem(companyId: Int): CompanyItem = transaction {
        CompaniesTable
            .selectAll().where { CompaniesTable.companyId eq companyId }
            .single()
            .toCompany()
    }

    override fun getVacancyList(): List<VacancyItem> = transaction {
        VacanciesTable
            .selectAll()
            .map { it.toVacancy() }
    }

    override fun getVacanciesForCompany(companyId: Int): List<VacancyItem> = transaction {
        VacanciesTable
            .selectAll().where { VacanciesTable.companyId eq companyId }
            .map { it.toVacancy() }
    }

    override fun getVacancyItem(vacancyId: Int): VacancyItem = transaction {
        VacanciesTable
            .selectAll().where { VacanciesTable.vacancyId eq vacancyId }
            .single()
            .toVacancy()
    }

    override fun getCompanyNameByVacancyId(vacancyId: Int): String {
        TODO("Not yet implemented")
    }

    override fun getResumeList(): List<ResumeItem> = transaction {
        ResumesTable
            .selectAll()
            .toList()
            .toResumesList()
    }

    override fun getResume(resumeId: Int): ResumeItem = transaction {
        val resultRow = ResumesTable
            .selectAll().where { ResumesTable.resumeId eq resumeId }
            .singleOrNull()

        if (resultRow != null) {
            resultRow.toResume()
        } else {
            val newResume = ResumeItem(
                id = 0,
                candidateInfo = CandidateInfo(
                    name = "",
                    profession = "",
                    "",
                    birthDate = java.time.LocalDate.now(),
                    contacts = Contacts("", ""),
                    relocation = false
                ),
                education = emptyList(),
                jobExperience = emptyList(),
                freeForm = "",
                tags = Tags(emptyList())
            )

            insertResume(newResume)
            newResume
        }
    }

    override fun insertResume(resume: ResumeItem): Unit = transaction {
        ResumesTable.insert {
            it[candidateName] = resume.candidateInfo.name
            it[profession] = resume.candidateInfo.profession
            it[gender] = resume.candidateInfo.sex
            it[relocation] = resume.candidateInfo.relocation
            it[email] = resume.candidateInfo.contacts.email
            it[phone] = resume.candidateInfo.contacts.phone
            it[freeForm] = resume.freeForm
            it[birthDate] = resume.candidateInfo.birthDate.toKotlinLocalDate()
        }
    }

    override fun getEducationForResume(resumeId: Int): List<Education> = transaction {
        EducationsTable
            .selectAll().where { EducationsTable.resumeId eq resumeId }
            .toList()
            .toEducationsList()
    }

    override fun getJobExperienceForResume(resumeId: Int): List<JobExperience> = transaction {
        ExperiencesTable
            .selectAll().where { ExperiencesTable.resumeId eq resumeId }
            .toList()
            .toJobExperienceList()
    }

    override fun updateResume(newResume: ResumeDto) = transaction {
        val existingResume = ResumesTable.selectAll().where { ResumesTable.resumeId eq newResume.id }
            .singleOrNull()
        if (existingResume != null) {
            ResumesTable.update({ ResumesTable.resumeId eq newResume.id }) { info ->
                info[candidateName] = newResume.candidateInfo.name
                info[gender] = newResume.candidateInfo.sex
                info[birthDate] = LocalDate.parse(newResume.candidateInfo.birthDate.toString())
                info[profession] = newResume.candidateInfo.profession
                info[relocation] = newResume.candidateInfo.relocation
                info[phone] = newResume.candidateInfo.contacts.phone
                info[email] = newResume.candidateInfo.contacts.email

                newResume.education.forEachIndexed { idx, education ->
                    EducationsTable.update({ EducationsTable.educationId eq idx + 1 }) {
                        it[educationType] = education.type
                        it[startYear] = education.yearStart.toString().toInt()
                        it[endYear] = education.yearEnd.toString().toInt()
                        it[description] = education.description

                    }
                }

                newResume.jobExperience.forEachIndexed { idx, job ->
                    ExperiencesTable.update({ ExperiencesTable.experienceId eq idx + 1}) {
                        it[companyName] = job.companyName
                        it[startDate] = LocalDate.parse("${job.dateStart}-01")
                        it[endDate] = LocalDate.parse("${job.dateEnd}-01")
                        it[description] = job.description
                    }
                }
            }
        }
    }

    override fun analyzeResume(): List<String> {
        var months: Long = 0
        val resume = getResume(0)
        resume.jobExperience.forEach {
            months += ChronoUnit.MONTHS.between(it.dateStart, it.dateEnd)
        }
        val years = months / 12
        val seniority = when (years) {
            in 0..2 -> "junior"
            in 3..5 -> "middle"
            else -> "senior"
        }
        val professionTag = resume.candidateInfo.profession.replace(" ", "_")
        logger.info("Experience: $years - $seniority")
        return listOf(seniority, professionTag)
    }

    override fun getTags(resumeId: Int): Tags = transaction {
        TagsTable
            .selectAll().where { TagsTable.resumeId eq resumeId }
            .toList()
            .toTagsList()
    }

    //    override fun getCompanyList(): List<CompanyItem> {
//        return jobDb
//    }
//
//    override fun getCompanyItem(companyId: Int): CompanyItem {
//        return jobDb[companyId]
//    }
//
//    override fun getVacancyList(): List<VacancyItem> {
//        val vacanciesList = mutableListOf<VacancyItem>()
//        for (item in jobDb) {
//            for (vacancy in item.vacancies) {
//                vacanciesList.add(vacancy)
//            }
//        }
//        return vacanciesList
//    }
//
//    override fun getVacancyItem(vacancyId: Int): VacancyItem {
//        val vacancyList = getVacancyList()
//        return vacancyList[vacancyId]
//    }
//
//    override fun getCompanyNameByVacancyId(vacancyId: Int): String {
//        var idCounter = 0
//        for (company in jobDb) {
//            for (vacancy in company.vacancies) {
//                if (idCounter == vacancyId) return company.name
//                ++idCounter
//            }
//        }
//        return ""
//    }
//
//    override fun getResumeList(): List<ResumeItem> {
//        return resumeDb
//    }
//
//    override fun getResume(resumeId: Int): ResumeItem {
//        return resumeDb[resumeId]
//    }
//
//    override fun updateResume(newResume: ResumeDto) {
//        val resume = Mapper.mapResumeDtoToResumeItem(newResume)
//        ResumeDb.instance.add(0, resume)
//    }
//
//    override fun analyzeResume(): List<String> {
//        var months: Long = 0
//        val resume = getResume(0)
//        resume.jobExperience.forEach {
//            months += ChronoUnit.MONTHS.between(it.dateStart, it.dateEnd)
//        }
//        val years = months / 12
//        val seniority = when (years) {
//            in 0..2 -> "junior"
//            in 3..5 -> "middle"
//            else -> "senior"
//        }
//        val professionTag = resume.candidateInfo.profession.replace(" ", "_")
//        logger.info("Experience: $years - $seniority")
//        return listOf(seniority, professionTag)
//    }
}