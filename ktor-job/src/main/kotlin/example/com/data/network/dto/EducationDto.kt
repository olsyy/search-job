package example.com.data.network.dto

import example.com.data.network.dto.serializers.YearSerializer
import kotlinx.serialization.Serializable
import java.time.Year

@Serializable
data class EducationDto(
    val type: String,
    @Serializable(with = YearSerializer::class) val yearStart: Year,
    @Serializable(with = YearSerializer::class) val yearEnd: Year,
    val description: String,
)
