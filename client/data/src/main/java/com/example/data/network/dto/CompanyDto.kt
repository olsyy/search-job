package com.example.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyDto(
    @SerialName("name") val name: String,
    @SerialName("activityField") val activityField: String,
)