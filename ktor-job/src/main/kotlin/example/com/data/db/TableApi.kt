package example.com.data.db

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date

object CompaniesTable : Table("companies") {
    val companyId = integer("company_id").autoIncrement()
    val companyName = varchar("company_name", 255)
    val activityField = varchar("activity_field", 255)
    val contacts = varchar("contacts", 255)

    override val primaryKey = PrimaryKey(companyId)
}

object VacanciesTable : Table("vacancies") {
    val vacancyId = VacanciesTable.integer("VACANCY_ID").autoIncrement()
    val companyId = integer("COMPANY_ID").references(CompaniesTable.companyId)
    val vacancyName = varchar("VACANCY_NAME", 255)
    val level = varchar("LEVEL", 255)
    val salary = integer("SALARY")

    override val primaryKey = PrimaryKey(vacancyId)
}

object ResumesTable : Table("resumes") {
    val resumeId = ResumesTable.integer("resume_id").autoIncrement()
    val candidateName = varchar("candidate_name", 255)
    val profession = varchar("profession", 255)
    val gender = varchar("gender", 255)
    val birthDate = date("birth_date")
    val phone = varchar("phone", 255)
    val email = varchar("email", 255)
    val relocation = bool("relocation")
    val freeForm = varchar("free_form", 255)

    override val primaryKey = PrimaryKey(resumeId)
}

object EducationsTable : Table("educations") {
    val educationId = EducationsTable.integer("education_id").autoIncrement()
    val resumeId = integer("resume_id").references(ResumesTable.resumeId)
    val educationType = varchar("education_type", 255)
    val startYear = integer("start_year")
    val endYear = integer("end_year")
    val description = varchar("description", 255)

    override val primaryKey = PrimaryKey(educationId)
}

object ExperiencesTable : Table("experiences") {
    val experienceId = ExperiencesTable.integer("experience_id").autoIncrement()
    val resumeId = integer("resume_id").references(ResumesTable.resumeId)
    val startDate = date("start_date")
    val endDate = date("end_date")
    val companyName = varchar("company_name", 255)
    val description = varchar("description", 255)

    override val primaryKey = PrimaryKey(experienceId)

}

object TagsTable : Table("tags") {
    private val tagId = TagsTable.integer("tag_id").autoIncrement()
    val resumeId = integer("resume_id").references(ResumesTable.resumeId)
    val tagName = varchar("tag_name", 255)

    override val primaryKey = PrimaryKey(tagId)

}