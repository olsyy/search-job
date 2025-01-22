package com.example.domain.entity

data class CandidateInfoItem(
    val name: String,
    val profession: String,
    var sex: String,
    val birthDate: String,
    val contacts: ContactsItem,
    val relocation: Boolean,
)
