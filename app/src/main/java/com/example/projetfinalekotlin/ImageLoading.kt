package com.example.projetfinalekotlin

import android.widget.ImageView
import com.squareup.picasso.Picasso

object ImageLoading {
    private const val URLImage = "https://flagcdn.com/w640/"
    private const val END_URL_IMAGE = ".png"

    fun loadFlagInto(countryCode: String, imageView: ImageView) {

        val url = "${URLImage}${countryCode.lowercase().subSequence(0, 2)}${END_URL_IMAGE}"
        Picasso.get().load(url).apply {
            placeholder(R.drawable.default_flag)
            error(R.drawable.default_flag)
            into(imageView)

        }
    }
}