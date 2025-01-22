package com.example.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CandidateInfoDto(
    val name: String,
    val profession: String,
    val sex: String,
    val birthDate: String,
    val contacts: ContactsDto,
    val relocation: Boolean,
)
