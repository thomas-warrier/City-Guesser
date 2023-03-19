package com.example.projetfinalekotlin.country

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.beust.klaxon.Klaxon
import com.example.projetfinalekotlin.MapsActivity
import com.example.projetfinalekotlin.Utils
import com.example.projetfinalekotlin.databinding.ActivityCountryBinding
import com.example.projetfinalekotlin.retrofit.GoogleAPI
import com.example.projetfinalekotlin.retrofit.HackerrankAPIAPI
import com.example.projetfinalekotlin.retrofit.RetrofitGoogleHelper
import com.example.projetfinalekotlin.retrofit.RetrofitHackerrankHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CountryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val googleAPI = RetrofitGoogleHelper.getInstance().create(GoogleAPI::class.java)
        val capitalAPI = RetrofitHackerrankHelper.getInstance().create(HackerrankAPIAPI::class.java)


        val result = Utils.getJsonFromKlaxon().toMutableList()

        result.let { countries ->
            val adapterCountry = CountryAdapter(countries.toMutableList()) {

                GlobalScope.launch {
                    googleAPI.getAddress(it.countryNameEn).body()?.let { address ->
                        capitalAPI.getCapital(it.countryNameEn).body()?.let { dataCapital ->
                            val info = dataCapital.data[0]
                            googleAPI.getAddress(info.capital).body()?.let { capitalAddress ->
                                val mapIntent =
                                    Intent(this@CountryActivity, MapsActivity::class.java)
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    mapIntent.putExtra("address", address)
                                    mapIntent.putExtra("capitalAddress", capitalAddress)

                                } else {
                                    mapIntent.putExtra("address", Klaxon().toJsonString(address))
                                    mapIntent.putExtra(
                                        "capitalAddress",
                                        Klaxon().toJsonString(capitalAddress)
                                    )
                                }
                                mapIntent.putExtra("capitalName", info.capital)
                                startActivity(mapIntent)
                            }


                        }
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