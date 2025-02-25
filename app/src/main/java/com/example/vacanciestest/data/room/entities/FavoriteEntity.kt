package com.example.vacanciestest.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "vacancy_id")
    val vacancyId: String
)