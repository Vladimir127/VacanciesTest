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

class FavoritesViewModel(application: Application): AndroidViewModel(application) {
    @Inject
    lateinit var vacanciesRepository: VacanciesRepository

    init {
        (application as MyApp).appComponent.inject(this)
    }

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
            _favoriteVacancies.value = vacanciesRepository.getFavorites()
        }
    }
}