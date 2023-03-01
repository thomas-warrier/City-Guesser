package com.example.projetfinalekotlin.country

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinalekotlin.R

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView
    val countryLogo: ImageView
    val view: View


    init {
        itemView.apply {
            name = findViewById(R.id.name)
            countryLogo = findViewById(R.id.countryLogo)
            view = this
        }

    }
}