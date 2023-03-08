package com.example.projetfinalekotlin

import android.widget.ImageView
import com.squareup.picasso.Picasso

object ImageLoading {
    private const val URLImage = "https://www.countryflagsapi.com/png/"

    fun loadFlagInto(countryCode: String, imageView: ImageView) {
        Picasso.get().load("${URLImage}${countryCode}").apply {
            placeholder(R.drawable.default_flag)
            error(R.drawable.default_flag)
            into(imageView)

        }
    }
}