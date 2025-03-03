package com.example.vacanciestest.data.room

import com.example.vacanciestest.data.room.entities.FavoriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource(private val favoriteDao: FavoriteDao) {
    suspend fun toggleFavorite(vacancyId: String) {
        withContext(Dispatchers.IO) {
            val favoriteCount = favoriteDao.isFavorite(vacancyId)
            if (favoriteCount > 0) {
                favoriteDao.deleteFavorite(vacancyId)
            } else {
                val entity = FavoriteEntity(vacancyId)
                favoriteDao.insertFavorite(entity)
            }
        }
    }

    suspend fun isFavorite(vacancyId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val favoriteCount = favoriteDao.isFavorite(vacancyId)
            favoriteCount > 0
        }
    }

    suspend fun getFavoritesCount(): Int {
        return withContext(Dispatchers.IO) {
            favoriteDao.getCount()
        }
    }

    suspend fun getFavorites(): List<FavoriteEntity> {
        return withContext(Dispatchers.IO) {
            favoriteDao.getAll()
        }
    }
}