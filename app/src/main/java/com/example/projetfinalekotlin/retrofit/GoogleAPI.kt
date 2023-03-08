package com.example.projetfinalekotlin.retrofit

import com.example.projetfinalekotlin.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleAPI {
    @GET("json?key=${BuildConfig.MAPS_API_KEY}")
    suspend fun getAddress(@Query("address") location: String): Response<Address>
}


data class Geometry(val location: LongitudeLatitude, val bounds: Bounds, val viewport: Bounds): java.io.Serializable
data class Bounds(val northeast: LongitudeLatitude, val southwest: LongitudeLatitude): java.io.Serializable
data class LongitudeLatitude(val lat: Double, val lng: Double) : java.io.Serializable
data class Result(val geometry: Geometry) : java.io.Serializable
data class Address(val results: List<Result>, val status: String) : java.io.Serializable