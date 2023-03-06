package com.example.projetfinalekotlin.country

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.beust.klaxon.Klaxon
import com.example.projetfinalekotlin.databinding.ActivityCountryBinding

class CountryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val result = Klaxon().parseArray<Country>(Countries.countriesString)

        result?.let { countries ->
            val adapterCountry = CountryAdapter(countries.toList()) {
                Toast.makeText(applicationContext, it.country, Toast.LENGTH_SHORT).show()

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