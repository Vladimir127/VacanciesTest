package com.example.vacanciestest.infrastructure

import android.app.Application
import com.example.vacanciestest.di.AppComponent
import com.example.vacanciestest.di.DaggerAppComponent
import com.example.vacanciestest.di.DbModule
import com.example.vacanciestest.di.NetworkModule

class MyApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .dbModule(DbModule(this))
            .build()
    }
}
