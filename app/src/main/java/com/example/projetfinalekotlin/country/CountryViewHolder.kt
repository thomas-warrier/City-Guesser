package com.example.projetfinalekotlin.country

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinalekotlin.R

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView
    val nativeName: TextView
    val region: TextView
    val countryLogo: ImageView
    val view: View


    init {
        itemView.apply {
            name = findViewById(R.id.name)
            nativeName = findViewById(R.id.nativeName)
            region = findViewById(R.id.region)
            countryLogo = findViewById(R.id.countryLogo)
            view = this
        }

    }
}