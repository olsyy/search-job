package com.example.data.network.dto

import com.example.data.network.dto.serializers.YearSerializer
import kotlinx.serialization.Serializable
import java.time.Year

@Serializable
data class EducationDto(
    val type: String,
    val yearStart: String,
    val yearEnd: String,
    val description: String,
)
