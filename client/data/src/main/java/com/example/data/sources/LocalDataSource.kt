package com.example.data.sources

import com.example.data.db.dao.ResumeDao
import com.example.data.db.entities.CandidateInfoEntity
import com.example.data.db.entities.EducationEntity
import com.example.data.db.entities.JobExperienceEntity
import com.example.domain.entity.CandidateInfoItem
import com.example.domain.entity.ContactsItem
import com.example.domain.entity.EducationItem
import com.example.domain.entity.JobExperienceItem
import com.example.domain.entity.ResumeItem
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: ResumeDao
) {

    suspend fun insertResume(resume: ResumeItem) {

//        dao.clearAllTables()

        val formatterLocalDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatterYearMonth = DateTimeFormatter.ofPattern("MM.yyyy")

        val newCandidateInfo = CandidateInfoEntity(
            candidateName = resume.candidateInfo.name,
            profession = resume.candidateInfo.profession,
            gender = resume.candidateInfo.sex,
            birthDate = LocalDate.parse(resume.candidateInfo.birthDate, formatterLocalDate),
            phone = resume.candidateInfo.contacts.phone,
            email = resume.candidateInfo.contacts.email,
            relocation = resume.candidateInfo.relocation,
            freeForm = resume.freeForm
        )
        dao.insertCandidateInfo(newCandidateInfo)
        resume.education.forEach {
            dao.insertEducation(
                EducationEntity(
                    educationType = it.type,
                    startYear = it.yearStart.toInt(),
                    endYear = it.yearEnd.toInt(),
                    description = it.description,
                )
            )
        }
        resume.jobExperience.forEach {
            dao.insertJobExperience(
                JobExperienceEntity(
                    startDate = YearMonth.parse(it.dateStart, formatterYearMonth),
                    endDate = YearMonth.parse(it.dateEnd, formatterYearMonth),
                    companyName = it.companyName,
                    description = it.description,
                )
            )
        }
    }

    suspend fun loadResume() : ResumeItem? {
        val candidateInfoList = dao.getCandidateInfo()
        if (candidateInfoList.isEmpty()) return null
        val candidateInfo = candidateInfoList[0]
        val birthDateFormatted = candidateInfo.birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        val educationList = dao.getEducationList().map { educationEntity ->
            EducationItem(
                type = educationEntity.educationType,
                yearStart = educationEntity.startYear.toString(),
                yearEnd = educationEntity.endYear.toString(),
                description = educationEntity.description
            )
        }
        val jobExperienceList = dao.getJobExperienceList().map { jobExperienceEntity ->
            val dateStartFormatted = jobExperienceEntity.startDate.format(DateTimeFormatter.ofPattern("MM.yyyy"))
            val dateEndFormatted = jobExperienceEntity.endDate.format(DateTimeFormatter.ofPattern("MM.yyyy"))
                JobExperienceItem(
                dateStart = dateStartFormatted,
                dateEnd = dateEndFormatted,
                companyName = jobExperienceEntity.companyName,
                description = jobExperienceEntity.description
            )
        }

        return ResumeItem(
            candidateInfo = CandidateInfoItem(
                name = candidateInfo.candidateName,
                profession = candidateInfo.profession,
                sex = candidateInfo.gender,
                birthDate = birthDateFormatted,
                contacts = ContactsItem(candidateInfo.phone, candidateInfo.email),
                relocation = candidateInfo.relocation
            ),
            education = educationList,
            jobExperience = jobExperienceList,
            freeForm = candidateInfo.freeForm,
            tags = listOf()
        )
    }

    suspend fun clearResumeLocally() {
        dao.clearAllTables()
    }
}