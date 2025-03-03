package com.example.vacanciestest.data.repositories

import com.example.vacanciestest.data.retrofit.WebDataSource
import com.example.vacanciestest.data.room.LocalDataSource
import com.example.vacanciestest.domain.models.Offer
import com.example.vacanciestest.domain.models.Vacancy
import com.example.vacanciestest.domain.repository.VacanciesRepository

class CombinedVacanciesRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val webDataSource: WebDataSource
) : VacanciesRepository {

    override suspend fun getVacancies(): List<Vacancy> {
        val vacancies = webDataSource.getVacancies()
        val favoriteEntities = localDataSource.getFavorites()
        val favoriteIds = favoriteEntities.map { it.vacancyId }

        for (vacancy in vacancies) {
            val isFavorite = favoriteIds.contains(vacancy.id)
            vacancy.isFavorite = isFavorite
        }

        return vacancies
    }

    override suspend fun getFirstThreeVacancies(): List<Vacancy> {
        val vacancies = webDataSource.getFirstThreeVacancies()
        val favoriteEntities = localDataSource.getFavorites()
        val favoriteIds = favoriteEntities.map { it.vacancyId }

        for (vacancy in vacancies) {
            val isFavorite = favoriteIds.contains(vacancy.id)
            vacancy.isFavorite = isFavorite
        }

        return vacancies
    }

    override suspend fun getOffers(): List<Offer> {
        return webDataSource.getOffers()
    }

    override suspend fun getVacancy(vacancyId: String): Vacancy? {
        val vacancy = webDataSource.getVacancy(vacancyId)

        vacancy?.let {
            if (localDataSource.isFavorite(vacancyId)) {
                vacancy.isFavorite = true
            }
        }

        return vacancy
    }

    override suspend fun toggleFavorite(vacancyId: String) {
        localDataSource.toggleFavorite(vacancyId)
    }

    override suspend fun getVacanciesCount(): Int {
        return webDataSource.getVacanciesCount()
    }

    override suspend fun getFavoritesCount(): Int {
        return localDataSource.getFavoritesCount()
    }

    override suspend fun getFavorites(): List<Vacancy> {
        val favorites = localDataSource.getFavorites()
        val vacancies = mutableListOf<Vacancy>()

        for (favoriteEntity in favorites) {
            val vacancy = webDataSource.getVacancy(favoriteEntity.vacancyId)
            vacancy?.let {
                it.isFavorite = true
                vacancies.add(it)
            }
        }

        return vacancies
    }
}