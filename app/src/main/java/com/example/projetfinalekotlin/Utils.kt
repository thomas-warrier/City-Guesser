package com.example.projetfinalekotlin

import com.beust.klaxon.*
import com.example.projetfinalekotlin.country.Countries
import com.example.projetfinalekotlin.country.Country
import java.io.StringReader



object Utils {
    fun getJsonFromKlaxon(): ArrayList<Country> {
        val klaxon = Klaxon()
        val countriesArray = arrayListOf<Country>()
        JsonReader(StringReader(Countries.countries)).use {
                reader -> reader.beginArray {
                    while (reader.hasNext()) {
                        val product = klaxon.parse<Country>(reader)
                        countriesArray.add(product!!)
                    }
                }
        }
        return countriesArray
    }
}