package com.example.projetfinalekotlin.retrofit

import com.example.projetfinalekotlin.BuildConfig
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GoogleAPI {
    @GET("json?key=${BuildConfig.MAPS_API_KEY}")
    suspend fun getAddress(@Query("address") location: String): Response<Address>

}

object RetrofitHelper {

    private const val baseUrl =
        "https://maps.googleapis.com/maps/api/geocode/"


    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}