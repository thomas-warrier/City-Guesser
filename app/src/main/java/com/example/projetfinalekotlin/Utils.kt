package com.example.projetfinalekotlin

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import com.example.projetfinalekotlin.country.Countries
import com.example.projetfinalekotlin.country.Country
import java.io.StringReader


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

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}