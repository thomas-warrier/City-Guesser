package com.example.projetfinalekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projetfinalekotlin.databinding.ActivityCountryBinding

class CountryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}