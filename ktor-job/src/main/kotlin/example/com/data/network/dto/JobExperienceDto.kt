package example.com.data.network.dto

import example.com.data.network.dto.serializers.YearMonthSerializer
import kotlinx.serialization.Serializable
import java.time.YearMonth

@Serializable
data class JobExperienceDto(
    @Serializable(with = YearMonthSerializer::class) val dateStart: YearMonth,
    @Serializable(with = YearMonthSerializer::class) val dateEnd: YearMonth,
    val companyName: String,
    val description: String,
)
