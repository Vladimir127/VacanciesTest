package com.example.vacanciestest.di

import com.example.vacanciestest.data.repositories.CombinedVacanciesRepositoryImpl
import com.example.vacanciestest.data.retrofit.WebDataSource
import com.example.vacanciestest.data.room.LocalDataSource
import com.example.vacanciestest.domain.repository.VacanciesRepository
import dagger.Module
import dagger.Provides

@Module(includes = [DbModule::class, NetworkModule::class])
class RepositoryModule {
    @Provides
    fun provideVacanciesRepository(localDataSource: LocalDataSource, webDataSource: WebDataSource): VacanciesRepository {
        return CombinedVacanciesRepositoryImpl(
            localDataSource,
            webDataSource
        )
    }
}