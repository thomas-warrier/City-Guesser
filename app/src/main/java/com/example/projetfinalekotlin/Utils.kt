package com.example.projetfinalekotlin

import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import com.example.projetfinalekotlin.country.Countries
import com.example.projetfinalekotlin.country.Country
import com.google.android.gms.maps.model.LatLng
import java.io.StringReader
import kotlin.math.pow
import kotlin.math.sqrt


object Utils {
    fun getJsonFromKlaxon(): ArrayList<Country> {
        val klaxon = Klaxon()
        val countriesArray = arrayListOf<Country>()
        JsonReader(StringReader(Countries.countries)).use { reader ->
            reader.beginArray {
                while (reader.hasNext()) {
                    val product = klaxon.parse<Country>(reader)
                    countriesArray.add(product!!)
                }
            }
        }
        return countriesArray
    }

    fun calculateDistance(latLngClick: LatLng, latLngCity: LatLng): Double {
        val distLat = latLngClick.latitude - latLngCity.latitude
        val distLong = latLngClick.longitude - latLngCity.longitude
        return sqrt(distLat.pow(2.0) + distLong.pow(2.0))
    }
}