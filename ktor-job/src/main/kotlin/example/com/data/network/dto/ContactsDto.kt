package example.com.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ContactsDto(
    val phone: String,
    val email: String,
)

