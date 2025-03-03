package com.example.vacanciestest.domain.models

data class Vacancy(
    val id: String,
    val lookingNumber: Int,
    val title: String,
    val address: Address,
    val company: String,
    val experience: Experience,
    val publishedDate: String,
    var isFavorite: Boolean,
    val salary: Salary,
    val schedules: ArrayList<String>,
    val appliedNumber: Int,
    val description: String,
    val responsibilities: String,
    val questions: ArrayList<String>
)