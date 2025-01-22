package com.example.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class VacancyDto(
    val profession: String,
    val level: String,
    val salary: Int,
    val companyName: String = "",
)