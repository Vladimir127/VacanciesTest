package com.example.vacanciestest.di

import com.example.vacanciestest.data.retrofit.JobService
import com.example.vacanciestest.data.retrofit.WebDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://drive.usercontent.google.com/u/0/"

@Module
class NetworkModule {
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideJobService(retrofit: Retrofit): JobService {
        return retrofit.create(JobService::class.java)
    }

    @Provides
    fun provideWebDataSource(jobService: JobService): WebDataSource {
        return WebDataSource(jobService)
    }
}