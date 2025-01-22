package example.com.domain.entity

import java.time.LocalDate

data class CandidateInfo(
    val name: String,
    val profession: String,
    val sex: String,
    val birthDate: LocalDate,
    val contacts: Contacts,
    val relocation: Boolean,
)
