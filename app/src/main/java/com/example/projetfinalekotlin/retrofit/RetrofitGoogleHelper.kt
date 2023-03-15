package com.example.projetfinalekotlin.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitGoogleHelper {

    private const val baseUrl =
        "https://maps.googleapis.com/maps/api/geocode/"


    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object RetrofitHackerrankHelper {

    private const val baseUrl =
        "https://jsonmock.hackerrank.com/api/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}