package example.com.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompaniesDto (
    @SerialName("companies") val list: List<CompanyDto>
)