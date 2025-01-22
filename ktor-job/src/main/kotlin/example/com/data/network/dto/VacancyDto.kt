package example.com.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class VacancyDto(
    val id: Int,
    val companyName: String,
    val profession: String,
    val level: String,
    val salary: Int,
    val description: String = "Description",
)