package example.com.domain.entity

import example.com.data.network.dto.Tags

data class ResumeItem(
    val id: Int,
    val candidateInfo: CandidateInfo,
    val education: List<Education>,
    val jobExperience: List<JobExperience>,
    val freeForm: String,
    val tags: Tags
)