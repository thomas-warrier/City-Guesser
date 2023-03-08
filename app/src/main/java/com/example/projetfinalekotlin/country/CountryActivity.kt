package com.example.projetfinalekotlin.country

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.beust.klaxon.Klaxon
import com.example.projetfinalekotlin.MapsActivity
import com.example.projetfinalekotlin.Utils
import com.example.projetfinalekotlin.databinding.ActivityCountryBinding
import com.example.projetfinalekotlin.retrofit.GoogleAPI
import com.example.projetfinalekotlin.retrofit.RetrofitHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CountryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val googleAPI = RetrofitHelper.getInstance().create(GoogleAPI::class.java)


        val result = Utils.getJsonFromKlaxon().toMutableList()

        result.let { countries ->
            val adapterCountry = CountryAdapter(countries.toMutableList()) {
                Toast.makeText(applicationContext, it.countryCode, Toast.LENGTH_SHORT).show()

                GlobalScope.launch {
                    googleAPI.getAddress(it.countryNameEn).body()?.let { address ->
                        val mapIntent = Intent(this@CountryActivity, MapsActivity::class.java)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            mapIntent.putExtra("address", address)
                        } else {
                            mapIntent.putExtra("address", Klaxon().toJsonString(address))
                        }
                        startActivity(mapIntent)
                    }
                }

            }


            binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        adapterCountry.setFilter(it)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        adapterCountry.setFilter(it)
                    }
                    return true
                }

            })

            binding.recycleView.apply {
                adapter = adapterCountry
                layoutManager = LinearLayoutManager(this@CountryActivity)
            }

        }


    }
}