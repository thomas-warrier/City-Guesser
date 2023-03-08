package com.example.projetfinalekotlin.country

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinalekotlin.R
import com.squareup.picasso.Picasso

class CountryAdapter(
    val countryList: MutableList<Country>,
    val onClick: (country: Country) -> Unit
) :
    RecyclerView.Adapter<CountryViewHolder>() {

    companion object {
        private const val URLImage = "https://www.countryflagsapi.com/png/"
    }

    val filtredList: MutableList<Country>

    init {
        countryList.removeAll { c -> c.code.isBlank() || c.country.isBlank() }
        filtredList = countryList.toMutableList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val eventView = inflater.inflate(R.layout.card_country, parent, false)
        return CountryViewHolder(eventView)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = filtredList[position]
        holder.view.setOnClickListener {
            onClick(country)
        }

        Picasso.get().load("$URLImage${country.code}").apply {
            error(R.drawable.default_flag)
            into(holder.countryLogo)
            placeholder(R.drawable.default_flag)
        }





        holder.name.text = country.country
        if (country.flag.isBlank()) {
            holder.countryLogo.setImageResource(R.drawable.default_flag)
        }
    }

    fun setFilter(filtre: String) {
        filtredList.clear()
        if (filtre.isBlank()) {
            filtredList.addAll(countryList)
        } else {
            for (country in countryList) {
                if (country.country.contains(filtre)) {
                    filtredList.add(country)
                }
            }
        }
        notifyDataSetChanged()

    }


    override fun getItemCount(): Int {
        return filtredList.size
    }

}