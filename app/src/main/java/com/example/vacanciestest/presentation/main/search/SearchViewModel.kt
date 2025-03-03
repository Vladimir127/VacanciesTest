package com.example.vacanciestest.presentation.main.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vacanciestest.domain.models.Offer
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

    private val _vacanciesCount: MutableLiveData<Int> = MutableLiveData()
    val vacanciesCount: LiveData<Int>
        get() = _vacanciesCount

    private val _offers: MutableLiveData<List<Offer>> = MutableLiveData()
    val offers: LiveData<List<Offer>>
        get() = _offers

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable>
        get() = _error

    fun loadVacancies() {
        viewModelScope.launch {
            try {
                val vacancies = vacanciesRepository.getFirstThreeVacancies()
                _vacancies.value = vacancies
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = e
            }
        }
    }

    fun loadOffers() {
        viewModelScope.launch {
            try {
                val offers = vacanciesRepository.getOffers()
                _offers.value = offers
            } catch (e: Exception) {
                e.printStackTrace()
                _offers.value = emptyList()
            }
        }
    }

    fun loadVacanciesCount() {
        viewModelScope.launch {
            try {
                val vacanciesCount = vacanciesRepository.getVacanciesCount()
                _vacanciesCount.value = vacanciesCount
            } catch (e: Exception) {
                e.printStackTrace()
                _vacanciesCount.value = 0
            }
        }
    }
}