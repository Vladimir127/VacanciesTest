package com.example.vacanciestest.domain.repository

import com.example.vacanciestest.domain.models.Offer
import com.example.vacanciestest.domain.models.Vacancy

interface VacanciesRepository {
    suspend fun getVacancies(): List<Vacancy>
    
    suspend fun getFirstThreeVacancies(): List<Vacancy>

    suspend fun getOffers(): List<Offer>

    suspend fun getVacancy(vacancyId: String): Vacancy?

    suspend fun toggleFavorite(vacancyId: String)

    suspend fun getVacanciesCount(): Int

    suspend fun getFavoritesCount(): Int

    suspend fun getFavorites(): List<Vacancy>
}