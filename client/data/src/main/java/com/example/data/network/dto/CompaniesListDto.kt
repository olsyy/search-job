package com.example.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompaniesListDto (
    @SerialName("companies") val companies: List<CompanyDto>
)