package com.example.vacanciestest.data.retrofit

import com.example.vacanciestest.domain.models.VacanciesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface JobService {
    @GET("uc")
    suspend fun getVacancies(
        @Query("id") id: String,
        @Query("export") export: String = "download"
    ): VacanciesResponse
}