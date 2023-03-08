package com.example.projetfinalekotlin

import com.beust.klaxon.*

data class Country(
    @Json(name = "id")
    val id : Int,
    @Json(name = "alpha3")
    val countryCode : String,
    @Json(name = "fr")
    val countryNameFr : String,
    @Json(name = "en")
    val countryNameEn : String
)
