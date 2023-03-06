package com.example.projetfinalekotlin.country

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.beust.klaxon.Klaxon
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


        //Test
        val googleAPI = RetrofitHelper.getInstance().create(GoogleAPI::class.java)
        // launching a new coroutine
        GlobalScope.launch {
            val result = googleAPI.getAddress("France")
            Log.d("API: ", result.body().toString())
        }

        //end Test

        val result = Klaxon().parseArray<Country>(Countries.countriesString)

        result?.let { countries ->
            val adapterCountry = CountryAdapter(countries.toMutableList()) {
                Toast.makeText(applicationContext, it.code, Toast.LENGTH_SHORT).show()

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