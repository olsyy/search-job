package example.com.domain.entity

data class CompanyItem(
    val id: Int,
    val name: String,
    val activityField: String,
    val vacancies: List<VacancyItem>,
    val contacts: String,
)