package com.example.data.network.dto

import com.example.data.network.dto.serializers.YearMonthSerializer
import kotlinx.serialization.Serializable
import java.time.YearMonth

@Serializable
data class JobExperienceDto(
    val dateStart: String,
    val dateEnd: String,
    val companyName: String,
    val description: String,
)
