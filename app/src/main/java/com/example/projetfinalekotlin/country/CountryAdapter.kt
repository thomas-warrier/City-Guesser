package com.example.projetfinalekotlin.country

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projetfinalekotlin.ImageLoading
import com.example.projetfinalekotlin.R
import com.example.projetfinalekotlin.data.SaveData
import com.example.projetfinalekotlin.retrofit.RetrofitHackerrankHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryAdapter(
    private val countryList: MutableList<Country>,
    val onClick: (country: Country) -> Unit
) :
    RecyclerView.Adapter<CountryViewHolder>() {

    private val filtredList: MutableList<Country>

    init {
        countryList.removeAll { c -> c.countryCode.isBlank() || c.countryNameFr.isBlank() }
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
        ImageLoading.loadFlagInto(country.countryCode, holder.countryLogo)

        holder.name.text = country.countryNameFr
        holder.countryCode.text = "code : ${country.countryCode.uppercase()}"

        SaveData.getBoolean(holder.view.context, country.countryCode)
            ?.let { isAlreadyHaveBeenDone ->
                if (isAlreadyHaveBeenDone) {
                    holder.trophyImage.visibility = View.VISIBLE
                } else {
                    holder.trophyImage.visibility = View.INVISIBLE
                }
            }

        GlobalScope.launch {
            RetrofitHackerrankHelper.getAPIInstance().getCapital(country.countryNameEn).body()
                ?.let { dataCapital ->
                    if (dataCapital.data.isNotEmpty()) {
                        val info = dataCapital.data[0]
                        withContext(Dispatchers.Main) {
                            holder.capitalName.text = "capital : ${info.capital}"
                        }
                    }

                }
        }
    }

    fun setFilter(newFiltre: String) {
        val filtre = newFiltre.lowercase()
        filtredList.clear()
        if (filtre.isBlank()) {
            filtredList.addAll(countryList)
        } else {
            for (country in countryList) {
                //recherche en anglais et français
                if (country.countryNameFr.lowercase()
                        .contains(filtre) || country.countryNameEn.lowercase().contains(filtre)
                ) {
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