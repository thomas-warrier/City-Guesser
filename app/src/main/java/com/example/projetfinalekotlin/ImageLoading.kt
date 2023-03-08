package com.example.projetfinalekotlin

import android.widget.ImageView
import com.squareup.picasso.Picasso

object ImageLoading {
    private const val URLImage = "https://www.countryflagsapi.com/png/"

    fun loadFlagInto(countryCode: String, imageView: ImageView) {
        Picasso.get().load("${URLImage}${countryCode}").apply {
            error(R.drawable.default_flag)
            into(imageView)
            placeholder(R.drawable.default_flag)
        }
    }
}