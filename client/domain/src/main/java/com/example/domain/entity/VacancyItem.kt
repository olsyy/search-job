package com.example.domain.entity

data class VacancyItem(
    val profession: String,
    val level: String,
    val salary: Int,
    val companyName: String,
    val description: String = "Description",
)
