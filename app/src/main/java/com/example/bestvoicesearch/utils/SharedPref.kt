package com.example.bestvoicesearch.utils

import android.content.Context

object SharedPref {


    val DARKMODE_PREF : String = "nightModePrefs"
    val KEY_ISNIGHTMODE :String = "isNightMode"

    // Save night mode state in SharedPreferences
    fun saveNightModeStateToSharedPref(context: Context?, nightMode:Boolean){
        val sharedPreferences = context?.getSharedPreferences(DARKMODE_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        if (editor != null) {
            editor.putBoolean(KEY_ISNIGHTMODE, nightMode)
            editor.apply()
        }
    }

    fun getNightModeStateFromSharedPref(context: Context?) : Boolean{
        val sharedPreferences = context?.getSharedPreferences(DARKMODE_PREF, Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)
        }
        return false
    }
}