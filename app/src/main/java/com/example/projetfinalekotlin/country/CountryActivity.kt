package com.example.projetfinalekotlin.country

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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



        result?.let {

            binding.recycleView.apply {
                adapter = CountryAdapter(it.toMutableList())
                layoutManager = LinearLayoutManager(this@CountryActivity)
            }

        }


    }
}