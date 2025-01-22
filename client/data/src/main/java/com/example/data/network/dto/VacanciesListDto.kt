package com.example.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VacanciesListDto (
    @SerialName("vacancies") val vacancies: List<VacancyDto>
)