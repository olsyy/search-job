package example.com.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CompanyDto(
    val name: String,
    val activityField: String,
)