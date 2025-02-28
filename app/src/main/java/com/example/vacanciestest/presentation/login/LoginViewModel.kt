package com.example.vacanciestest.presentation.login

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _emailValid = MutableLiveData<Boolean>()
    val emailValid: LiveData<Boolean>
        get() = _emailValid

    /** LiveData, хранящая признак доступности кнопки "Продолжить" на первом шаге (ввод e-mail) */
    private val _continueButtonEnabled = MutableLiveData<Boolean>().apply { value = false }
    val continueButtonEnabled: LiveData<Boolean> = _continueButtonEnabled

    /** LiveData, хранящая признак доступности кнопки "Подтвердить" на втором шаге (ввод кода) */
    private val _confirmButtonEnabled = MutableLiveData<Boolean>().apply { value = false }
    val confirmButtonEnabled: LiveData<Boolean> = _confirmButtonEnabled

    /** Список для проверки цифр кода */
    private val codes = MutableList(4) { MutableLiveData<String>() }

    init {
        // Инициализируем LiveData
        codes.forEach { code -> code.observeForever { validateInput() } }
    }

    fun validateEmail(name: String) {
        _emailValid.value = name.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(name).matches()
        checkFieldsValidity()
    }

    private fun checkFieldsValidity() {
        val isEmailValid = _emailValid.value ?: false
        _continueButtonEnabled.value = isEmailValid
    }

    fun updateCode(index: Int, value: String) {
        codes[index].value = value
    }

    private fun validateInput() {
        _confirmButtonEnabled.value = codes.all { it.value?.isNotEmpty() == true && it.value != "*" }
    }
}