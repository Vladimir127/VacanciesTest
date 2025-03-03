package com.example.vacanciestest.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vacanciestest.data.room.entities.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getAll(): List<FavoriteEntity>

    @Query("SELECT COUNT(*) FROM favorites")
    fun getCount(): Int

    @Query("SELECT COUNT(*) FROM favorites WHERE vacancy_id = :vacancyId")
    fun isFavorite(vacancyId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(entity: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE vacancy_id = :vacancyId")
    fun deleteFavorite(vacancyId: String)
}