package example.com.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VacanciesDto (
    @SerialName("vacancies") val list: List<VacancyDto>
)