package com.example.projetfinalekotlin.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryCodeAPI {
    @GET("countries/iso3/{code}")
    suspend fun getInfoISO3(@Path("code") countryCode: String): Response<List<CountryFromAPI>>
}


data class ListCountry(val data: List<CountryFromAPI>) : java.io.Serializable
data class CountryFromAPI(
    val country_name: String,
    val capital_city: String,
    val continent: String,
    val ISO2: String,
    val ISO3: String
) :
    java.io.Serializable
