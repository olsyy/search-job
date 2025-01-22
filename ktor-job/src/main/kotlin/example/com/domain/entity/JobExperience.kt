package example.com.domain.entity

import java.time.YearMonth

data class JobExperience(
    val dateStart: YearMonth,
    val dateEnd: YearMonth,
    val companyName: String,
    val description: String,
)
