package example.com.domain.entity

data class VacancyItem(
    val id: Int,
    val companyId: Int,
    val profession: String,
    val level: String,
    val salary: Int,
    val description: String = "Description",
)
