package example.com.data.network.dto

import example.com.data.network.dto.serializers.DateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class CandidateInfoDto(
    val name: String,
    val profession: String,
    val sex: String,
    @Serializable(with = DateSerializer::class) val birthDate: LocalDate,
    val contacts: ContactsDto,
    val relocation: Boolean,
)
