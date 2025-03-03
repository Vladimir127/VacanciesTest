package com.example.vacanciestest.domain.models

data class Offer(
    val id: String?,
    val title: String,
    val button: OfferButton?,
    val link: String
)