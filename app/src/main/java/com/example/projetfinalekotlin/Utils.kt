package com.example.projetfinalekotlin

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset
import com.beust.klaxon.*
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