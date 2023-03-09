package com.android.rideway_app.mainapp

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil (context : Context){
    private val prefs : SharedPreferences =
        context.getSharedPreferences("UserProfile",Context.MODE_PRIVATE)

    fun getString(key : String , def : String) : String{
        return prefs.getString(key,def).toString()
    }

    fun getUserId() : Int {
        return prefs.getInt("user_id",0)
    }

    fun getUserToken() : String {
        return prefs.getString("token","")!!
    }

    fun setString(key : String , value : String) {
        prefs.edit().putString(key,value).apply()
    }
}