package com.example.projetfinalekotlin.data

import android.content.Context
import android.content.Context.MODE_PRIVATE

import android.content.SharedPreferences


object SaveData {
    private fun getSharedPref(context: Context): SharedPreferences? {
        return context.getSharedPreferences("MySharedPref", MODE_PRIVATE)
    }

    fun putBoolean(context: Context, key: String, value: Boolean) {
        val edit = getSharedPref(context)?.edit()
        edit?.putBoolean(key, value)
        edit?.apply()
    }

    fun getBoolean(context: Context, key: String): Boolean? {
        return getSharedPref(context)?.getBoolean(key, false)
    }
}