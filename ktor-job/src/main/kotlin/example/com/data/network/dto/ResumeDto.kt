package example.com.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResumeDto(
    val id: Int,
    val candidateInfo: CandidateInfoDto,
    val education: List<EducationDto>,
    val jobExperience: List<JobExperienceDto>,
    val freeForm: String,
    val tags: List<String> = mutableListOf(),
)