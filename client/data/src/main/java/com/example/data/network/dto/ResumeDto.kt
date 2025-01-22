package com.example.data.network.dto

import com.example.data.network.dto.CandidateInfoDto
import com.example.data.network.dto.EducationDto
import com.example.data.network.dto.JobExperienceDto
import kotlinx.serialization.Serializable

@Serializable
data class ResumeDto(
    val id: Int = 1,
    val candidateInfo: CandidateInfoDto,
    val education: List<EducationDto>,
    val jobExperience: List<JobExperienceDto>,
    val freeForm: String,
    val tags: List<String> = mutableListOf(),
)