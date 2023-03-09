package com.android.rideway_app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitConnection {

    companion object{
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
        private var INSTANCE : Retrofit? = null

        fun getInstance() : Retrofit {
            if(INSTANCE==null){
                INSTANCE = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            }
            return INSTANCE!!
        }
    }
}