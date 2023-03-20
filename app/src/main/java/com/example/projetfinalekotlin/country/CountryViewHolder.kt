package com.example.projetfinalekotlin.country

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinalekotlin.R

class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView
    val countryCode: TextView
    val capitalName: TextView
    val countryLogo: ImageView
    val trophyImage: ImageView
    var mustClickOnIt: Boolean = false

    val view: View


    init {
        itemView.apply {
            name = findViewById(R.id.name)
            capitalName = findViewById(R.id.captialName)
            countryCode = findViewById(R.id.countryCode)
            countryLogo = findViewById(R.id.countryLogo)
            trophyImage = findViewById(R.id.image)
            view = this
        }

    }
}