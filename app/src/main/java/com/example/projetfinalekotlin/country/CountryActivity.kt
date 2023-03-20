package com.example.projetfinalekotlin.country

import android.app.AlertDialog
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
import com.example.projetfinalekotlin.retrofit.RetrofitCountryCodeHelper
import com.example.projetfinalekotlin.retrofit.RetrofitGoogleHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CountryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryBinding
    var adapterCountry: CountryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val googleAPI = RetrofitGoogleHelper.getAPIInstance()
        val countryAPI = RetrofitCountryCodeHelper.getAPIInstance()


        val countries = Utils.getJsonFromKlaxon().toMutableList()


        adapterCountry = CountryAdapter(countries.toMutableList()) {

            GlobalScope.launch {
                googleAPI.getAddress(it.countryNameEn).body()?.let { address ->
                    countryAPI.getInfoISO3(it.countryCode.uppercase()).body()?.let { listCountry ->

                        if (listCountry.isEmpty()) {
                            runOnUiThread {
                                Toast.makeText(
                                    applicationContext,
                                    "Désolé, nous n'avons pas pu trouver les informations ce pays.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        } else {
                            val info = listCountry[0]
                            googleAPI.getAddress(info.capital_city).body()?.let { capitalAddress ->
                                val mapIntent =
                                    Intent(this@CountryActivity, MapsActivity::class.java)
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    mapIntent.putExtra(
                                        MapsActivity.ADDRESS_COUNTRY_EXTRA,
                                        address
                                    )
                                    mapIntent.putExtra(
                                        MapsActivity.ADDRESS_CAPITAL_EXTRA,
                                        capitalAddress
                                    )

                                } else {
                                    mapIntent.putExtra(
                                        MapsActivity.ADDRESS_COUNTRY_EXTRA,
                                        Klaxon().toJsonString(address)
                                    )
                                    mapIntent.putExtra(
                                        MapsActivity.ADDRESS_CAPITAL_EXTRA,
                                        Klaxon().toJsonString(capitalAddress)
                                    )
                                }
                                mapIntent.putExtra(
                                    MapsActivity.CAPITAL_NAME_EXTRA,
                                    info.capital_city
                                )
                                mapIntent.putExtra(
                                    MapsActivity.COUNTRY_CODE_EXTRA,
                                    it.countryCode
                                )
                                startActivity(mapIntent)
                            }
                        }
                    }
                }
            }

        }


        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    adapterCountry?.setFilter(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    adapterCountry?.setFilter(it)
                }
                return true
            }

        })

        binding.recycleView.apply {
            adapter = adapterCountry
            layoutManager = LinearLayoutManager(this@CountryActivity)
        }


    }

    override fun onResume() {
        super.onResume()
        adapterCountry?.notifyDataSetChanged()
    }

    fun modalWindow(view: android.view.View) {
        val dialog = AlertDialog.Builder(this@CountryActivity)
        dialog.setTitle("Explications du jeu")
        dialog.setMessage("Le but du jeu est de trouver le pays correspondant à la capitale affichée. \nPour cela, il vous suffit de cliquer sur le bouton \"Jouer\". \nVous avez 5 essais pour trouver la bonne réponse.\nBonne chance !")
        dialog.setPositiveButton("C'est parti") { dialog, which ->
            dialog.dismiss()
        }
        dialog.show()
    }
}