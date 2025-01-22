package example.com.data.mapper

import example.com.data.db.*
import example.com.data.network.dto.*
import example.com.data.repository.RepositoryImpl.getCompanyItem
import example.com.data.repository.RepositoryImpl.getEducationForResume
import example.com.data.repository.RepositoryImpl.getJobExperienceForResume
import example.com.data.repository.RepositoryImpl.getTags
import example.com.data.repository.RepositoryImpl.getVacanciesForCompany
import example.com.domain.entity.*
import kotlinx.datetime.toJavaLocalDate
import org.jetbrains.exposed.sql.ResultRow
import java.time.Year
import java.time.YearMonth


fun CompanyItem.toCompanyDto() = CompanyDto(
    name = this.name,
    activityField = this.activityField
)

fun VacancyItem.toVacancyDto() = VacancyDto(
    id = this.id,
    companyName = getCompanyItem(this.companyId).name,
    profession = this.profession,
    level =  this.level,
    salary = this.salary,
    description = this.description
)
fun List<CompanyItem>.toCompaniesDto() = CompaniesDto(
    list = this.map { it.toCompanyDto() }
)

fun ResumeItem.toResumeDto() = ResumeDto(
    id = this.id,
    candidateInfo = this.candidateInfo.toDto(),
    education = this.education.map { it.toDto() },
    jobExperience = this.jobExperience.map { it.toDto() },
    freeForm = this.freeForm,
    tags = listOf("Junior", "Qa")
)

private fun CandidateInfo.toDto() = CandidateInfoDto(
    name = this.name,
    profession = this.profession,
    sex = this.sex,
    birthDate = this.birthDate,
    contacts = this.contacts.toDto(),
    relocation = this.relocation
)

private fun Contacts.toDto() = ContactsDto(
    phone = this.phone,
    email = this.email
)

private fun Education.toDto() = EducationDto(
    type = this.type,
    yearEnd = this.yearEnd,
    yearStart = this.yearStart,
    description = this.description
)

private fun JobExperience.toDto() = JobExperienceDto(
    dateStart = this.dateStart,
    dateEnd = this.dateEnd,
    companyName = this.companyName,
    description = this.description
)



fun ResumeDto.toResumeItem() = ResumeItem(
    id = this.id,
    candidateInfo = this.candidateInfo.toItem(),
    education = this.education.map { it.toItem() },
    jobExperience = this.jobExperience.map { it.toItem() },
    freeForm = this.freeForm,
    tags = Tags(this.tags)
)

private fun CandidateInfoDto.toItem() = CandidateInfo(
    name = this.name,
    profession = this.profession,
    sex = this.sex,
    birthDate = this.birthDate,
    contacts = this.contacts.toItem(),
    relocation = this.relocation
)

private fun ContactsDto.toItem() = Contacts(
    phone = this.phone,
    email = this.email
)

private fun EducationDto.toItem() = Education(
    type = this.type,
    yearEnd = this.yearEnd,
    yearStart = this.yearStart,
    description = this.description
)

private fun JobExperienceDto.toItem() = JobExperience(
    dateStart = this.dateStart,
    dateEnd = this.dateEnd,
    companyName = this.companyName,
    description = this.description
)

fun ResultRow.toCompany(): CompanyItem = CompanyItem(
    id = this[CompaniesTable.companyId],
    name = this[CompaniesTable.companyName],
    activityField = this[CompaniesTable.activityField],
    contacts = this[CompaniesTable.contacts],
    vacancies = getVacanciesForCompany(this[CompaniesTable.companyId])
)

fun ResultRow.toVacancy(): VacancyItem = VacancyItem(
    id = this[VacanciesTable.vacancyId],
    companyId = this[VacanciesTable.companyId],
    profession = this[VacanciesTable.vacancyName],
    level = this[VacanciesTable.level],
    salary = this[VacanciesTable.salary]
)

fun ResultRow.toResume(): ResumeItem = ResumeItem(
    id = this[ResumesTable.resumeId],
    candidateInfo = CandidateInfo(
        name = this[ResumesTable.candidateName],
        profession = this[ResumesTable.profession],
        sex = this[ResumesTable.gender],
        birthDate = this[ResumesTable.birthDate].toJavaLocalDate(),
        contacts = Contacts(
            phone = this[ResumesTable.phone],
            email = this[ResumesTable.email],
        ),
        relocation = this[ResumesTable.relocation]
    ),
    education = getEducationForResume(this[ResumesTable.resumeId]),
    jobExperience = getJobExperienceForResume(this[ResumesTable.resumeId]),
    freeForm = this[ResumesTable.freeForm],
    tags = getTags(this[ResumesTable.resumeId])
)

fun ResultRow.toEducation(): Education = Education(
    type = this[EducationsTable.educationType],
    yearStart = Year.of(this[EducationsTable.startYear]),
    yearEnd = Year.of(this[EducationsTable.endYear]),
    description = this[EducationsTable.description]
)

fun ResultRow.toJobExperience(): JobExperience = JobExperience(
    dateStart = YearMonth.of(this[ExperiencesTable.startDate].year, this[ExperiencesTable.startDate].month),
    dateEnd = YearMonth.of(this[ExperiencesTable.endDate].year, this[ExperiencesTable.endDate].month),
    companyName = this[ExperiencesTable.companyName],
    description = this[ExperiencesTable.description]
)

fun List<ResultRow>.toCompaniesList(): List<CompanyItem> = this.map { it.toCompany() }

fun List<ResultRow>.toVacanciesList(): List<VacancyItem> = this.map { it.toVacancy() }

fun List<ResultRow>.toResumesList(): List<ResumeItem> = this.map { it.toResume() }

fun List<ResultRow>.toEducationsList(): List<Education> = this.map { it.toEducation() }

fun List<ResultRow>.toJobExperienceList(): List<JobExperience> = this.map { it.toJobExperience() }
fun List<ResultRow>.toTagsList(): Tags = Tags(
    list = this.map { it[TagsTable.tagName] }
)