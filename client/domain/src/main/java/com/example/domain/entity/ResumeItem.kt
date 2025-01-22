package com.example.domain.entity

data class ResumeItem(
    val candidateInfo: CandidateInfoItem,
    val education: List<EducationItem>,
    val jobExperience: List<JobExperienceItem>,
    val freeForm: String,
    val tags: List<String>
) {
    fun copyWithoutTags(): ResumeItem {
        return this.copy(tags = emptyList())
    }
}