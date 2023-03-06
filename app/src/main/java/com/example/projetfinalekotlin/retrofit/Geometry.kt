package com.example.projetfinalekotlin.retrofit

data class Geometry(val location: LL, val bounds: Bounds, val viewport: Bounds): java.io.Serializable


data class Bounds(val northeast: LL, val southwest: LL): java.io.Serializable
