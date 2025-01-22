package example.com.domain.entity

import java.time.Year

data class Education(
    val type: String,
    val yearStart: Year,
    val yearEnd: Year,
    val description: String,
)
