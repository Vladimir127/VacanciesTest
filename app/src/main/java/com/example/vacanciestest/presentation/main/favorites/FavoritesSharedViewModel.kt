package com.example.vacanciestest.presentation.main.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vacanciestest.domain.models.Vacancy
import com.example.vacanciestest.domain.repository.VacanciesRepository
import com.example.vacanciestest.infrastructure.MyApp
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Общая ViewModel для управления избранным.
 * Используется для добавления элементов в избранное (и удаления их оттуда)
 * во фрагментах [SearchFragment] и [FavoritesFragment], а также немедленного отображения
 * количества избранных элементов в [MainActivity]
 */
class FavoritesSharedViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var vacanciesRepository: VacanciesRepository

    init {
        (application as MyApp).appComponent.inject(this)
    }

    private val _favoritesCount: MutableLiveData<Int> = MutableLiveData()
    val favoritesCount: LiveData<Int>
        get() = _favoritesCount

    private val _favoriteVacancies: MutableLiveData<List<Vacancy>> = MutableLiveData()
    val favoriteVacancies: LiveData<List<Vacancy>>
        get() = _favoriteVacancies

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() = _error

    fun loadFavorites(){
        viewModelScope.launch {
            try {
                val vacancies = vacanciesRepository.getFavorites()
                _favoriteVacancies.value = vacancies
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e
            }
        }
    }

    fun toggleFavorite(vacancyId: String) {
        viewModelScope.launch {
            vacanciesRepository.toggleFavorite(vacancyId)
            loadFavoritesCount()
            loadFavorites()
        }
    }

    fun loadFavoritesCount() {
        viewModelScope.launch {
            try {
                val count = vacanciesRepository.getFavoritesCount()
                _favoritesCount.value = count
            } catch (e: Exception) {
                e.printStackTrace()
                _favoritesCount.value = 0
            }
        }
    }
}