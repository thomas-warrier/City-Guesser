package com.example.projetfinalekotlin.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HackerrankAPIAPI {
    @GET("countries")
    suspend fun getCapital(@Query("name") nameCountry: String): Response<DataCapital>
}


data class DataCapital(val data: List<ResultCapital>) : java.io.Serializable
data class ResultCapital(
    val capital: String
) :
    java.io.Serializable
