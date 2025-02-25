package com.example.vacanciestest.domain.models

data class Vacancy(
    var id: String? = null,
    var lookingNumber: Int? = null,
    var title: String? = null,
    var address: Address? = Address(),
    var company: String? = null,
    var experience: Experience? = Experience(),
    var publishedDate: String? = null,
    var isFavorite: Boolean? = null,
    var salary: Salary? = Salary(),
    var schedules: ArrayList<String> = arrayListOf(),
    var appliedNumber: Int? = null,
    var description: String? = null,
    var responsibilities: String? = null,
    var questions: ArrayList<String> = arrayListOf()
)