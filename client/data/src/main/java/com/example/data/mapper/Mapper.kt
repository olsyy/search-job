package com.example.data.mapper

import com.example.data.network.dto.CandidateInfoDto
import com.example.data.network.dto.CompaniesListDto
import com.example.data.network.dto.CompanyDto
import com.example.data.network.dto.ContactsDto
import com.example.data.network.dto.EducationDto
import com.example.data.network.dto.JobExperienceDto
import com.example.data.network.dto.ResumeDto
import com.example.data.network.dto.VacanciesListDto
import com.example.data.network.dto.VacancyDto
import com.example.domain.entity.CandidateInfoItem
import com.example.domain.entity.CompaniesList
import com.example.domain.entity.CompanyItem
import com.example.domain.entity.ContactsItem
import com.example.domain.entity.EducationItem
import com.example.domain.entity.JobExperienceItem
import com.example.domain.entity.ResumeItem
import com.example.domain.entity.VacanciesList
import com.example.domain.entity.VacancyItem


    fun CompanyItem.toDto() = CompanyDto(
        name = this.name,
        activityField = this.activityField
    )

    fun CompanyDto.toItem() = CompanyItem(
        name = this.name,
        activityField = this.activityField
    )

    fun CompaniesList.toDtoList() = CompaniesListDto(
        companies = this.list.map { it.toDto() }
    )

    fun CompaniesListDto.toItemList() = CompaniesList(
        list = this.companies?.map { it.toItem() } ?: emptyList()
    )

    fun ResumeItem.toDto() = ResumeDto(
        candidateInfo = this.candidateInfo.toDto(),
        education = this.education.map { it.toDto() },
        jobExperience = this.jobExperience.map { it.toDto() },
        freeForm = this.freeForm,
        tags = listOf()
    )

    fun ResumeDto.toItem() = ResumeItem(
        candidateInfo = this.candidateInfo.toItem(),
        education = this.education.map { it.toItem() },
        jobExperience = this.jobExperience.map { it.toItem() },
        freeForm = this.freeForm,
        tags = this.tags
    )

    fun CandidateInfoItem.toDto() = CandidateInfoDto(
        name = this.name,
        profession = this.profession,
        sex = this.sex,
        birthDate = this.birthDate,
        contacts = ContactsDto(this.contacts.phone, this.contacts.email),
        relocation = this.relocation
    )

    fun CandidateInfoDto.toItem() = CandidateInfoItem(
        name = this.name,
        profession = this.profession,
        sex = this.sex,
        birthDate = this.birthDate,
        contacts = ContactsItem(this.contacts.phone, this.contacts.email),
        relocation = this.relocation
    )

    fun EducationItem.toDto() = EducationDto(
        type = this.type,
        yearEnd = this.yearEnd,
        yearStart = this.yearStart,
        description = this.description
    )

    fun EducationDto.toItem() = EducationItem(
        type = this.type,
        yearEnd = this.yearEnd,
        yearStart = this.yearStart,
        description = this.description
    )

    fun JobExperienceItem.toDto() = JobExperienceDto(
        dateStart = this.dateStart,
        dateEnd = this.dateEnd,
        companyName = this.companyName,
        description = this.description
    )

    fun JobExperienceDto.toItem() = JobExperienceItem(
        dateStart = this.dateStart,
        dateEnd = this.dateEnd,
        companyName = this.companyName,
        description = this.description
    )

    fun VacancyDto.toItem() = VacancyItem(
        profession = this.profession,
        level = this.level,
        salary = this.salary,
        companyName = this.companyName
    )

    fun VacanciesListDto.toItemList() = VacanciesList(
        list = this.vacancies.map { it.toItem() }
    )