package com.example.vacanciestest.data.retrofit

import com.example.vacanciestest.domain.models.Offer
import com.example.vacanciestest.domain.models.Vacancy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WebDataSource(private val jobService: JobService) {
    suspend fun getVacancies(): List<Vacancy> {
        return withContext(Dispatchers.IO) {
            try {
                jobService.getData("1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r").vacancies
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun getVacanciesCount(): Int {
        return withContext(Dispatchers.IO) {
            try {
                jobService.getData("1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r").vacancies.size
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun getFirstThreeVacancies(): List<Vacancy> {
        return withContext(Dispatchers.IO) {
            try {
                jobService.getData("1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r").vacancies.take(3)
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun getOffers(): List<Offer> {
        return withContext(Dispatchers.IO) {
            try {
                jobService.getData("1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r").offers
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