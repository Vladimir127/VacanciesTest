package com.example.vacanciestest.presentation.main.search

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

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var vacanciesRepository: VacanciesRepository

    init {
        (application as MyApp).appComponent.inject(this)
    }

    private val _vacancies: MutableLiveData<List<Vacancy>> = MutableLiveData()
    val vacancies: LiveData<List<Vacancy>>
        get() = _vacancies

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() = _error

    fun loadVacancies() {
        viewModelScope.launch {
            try {
                val vacancies = vacanciesRepository.getVacancies()
                _vacancies.value = vacancies
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e
            }
        }
    }

    fun toggleFavorite(vacancyId: String) {
        viewModelScope.launch {
            vacanciesRepository.toggleFavorite(vacancyId)
        }
    }
}