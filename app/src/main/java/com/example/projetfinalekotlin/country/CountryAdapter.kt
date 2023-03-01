package com.example.projetfinalekotlin.country

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinalekotlin.R

class CountryAdapter(
    val list: MutableList<Country>,
) :
    RecyclerView.Adapter<CountryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val eventView = inflater.inflate(R.layout.card_country, parent, false)
        return CountryViewHolder(eventView)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = list[position]
        holder.view.setOnClickListener {
            Toast.makeText(it.context, "Click $position", Toast.LENGTH_SHORT).show()
        }


        holder.name.text = country.country
        if (country.flag.isBlank()) {
            holder.countryLogo.setImageResource(R.drawable.default_flag)
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

}