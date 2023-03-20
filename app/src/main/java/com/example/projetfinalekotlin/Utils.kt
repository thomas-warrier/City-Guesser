package com.example.projetfinalekotlin

import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import com.example.projetfinalekotlin.country.Countries
import com.example.projetfinalekotlin.country.Country
import java.io.StringReader
import java.net.InetAddress


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

    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr: InetAddress = InetAddress.getByName("google.com")
            //You can replace it with your name
            !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }
}