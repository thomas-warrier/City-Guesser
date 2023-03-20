package com.example.projetfinalekotlin.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitCountryCodeHelper {


    private var instance: CountryCodeAPI? = null

    fun getAPIInstance(): CountryCodeAPI {
        if (instance == null) {
            instance = getInstance().create(CountryCodeAPI::class.java)
        }
        return instance!!
    }

    private const val baseUrl =
        "https://countrycode.dev/api/"


    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object RetrofitGoogleHelper {


    private var instance: GoogleAPI? = null

    fun getAPIInstance(): GoogleAPI {
        if (instance == null) {
            instance = getInstance().create(GoogleAPI::class.java)
        }
        return instance!!
    }

    private const val baseUrl =
        "https://maps.googleapis.com/maps/api/geocode/"


    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
