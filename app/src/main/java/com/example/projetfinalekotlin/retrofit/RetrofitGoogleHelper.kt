package com.example.projetfinalekotlin.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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


    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object RetrofitHackerrankHelper {

    private var instance: HackerrankAPIAPI? = null

    fun getAPIInstance(): HackerrankAPIAPI {
        if (instance == null) {
            instance = getInstance().create(HackerrankAPIAPI::class.java)
        }
        return instance!!
    }

    private const val baseUrl =
        "https://jsonmock.hackerrank.com/api/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}