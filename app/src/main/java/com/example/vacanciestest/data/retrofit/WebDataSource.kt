package com.example.vacanciestest.data.retrofit

import com.example.vacanciestest.domain.models.Vacancy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WebDataSource(private val jobService: JobService) {
    suspend fun getVacancies(): List<Vacancy> {
        return withContext(Dispatchers.IO) {
            try {
                // Чтобы обратиться к серверу без конкретного эндпоинта,
                // необходимо передать пустую строку в качестве URL
                jobService.getVacancies("1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r").vacancies
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun getVacancy(id: String): Vacancy? {
        val vacancies = getVacancies()
        return vacancies.find { it.id == id }
    }
}