package com.example.vacanciestest.di

import com.example.vacanciestest.presentation.main.favorites.FavoritesSharedViewModel
import com.example.vacanciestest.presentation.main.search.SearchViewModel
import dagger.Component

@Component(modules = [NetworkModule::class, DbModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(searchViewModel: SearchViewModel)
    fun inject(favoritesSharedViewModel: FavoritesSharedViewModel)
}