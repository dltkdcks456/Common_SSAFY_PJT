package com.android.rideway_app.retrofit

import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import retrofit2.converter.scalars.ScalarsConverterFactory


class RetrofitClient {

    companion object {
        private const val BASE_URL = "http://i8e102.p.ssafy.io:8080/api/"
//        private const val BASE_URL = "http://10.0.2.2:8080/api/"

        private var INSTANCE : Retrofit? = null
        private var SIMPLE_INSTANCE : Retrofit? = null
        private val tokenInterceptor : AppInterceptor = AppInterceptor()


        private fun okHttpClient(interceptor: AppInterceptor) : OkHttpClient{
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }

        fun getInstance() : Retrofit{
            if(INSTANCE == null){
                INSTANCE = Retrofit.Builder().
                    baseUrl(BASE_URL).
                    client(okHttpClient(tokenInterceptor)).
                    addConverterFactory(GsonConverterFactory.create()).
                    build()
            }
            return INSTANCE!!
        }

        fun getSimpleInstance() : Retrofit{
            if(SIMPLE_INSTANCE == null){
                SIMPLE_INSTANCE = Retrofit.Builder().
                baseUrl(BASE_URL).
                client(okHttpClient(tokenInterceptor)).
                addConverterFactory(ScalarsConverterFactory.create()).
                build()
            }
            return SIMPLE_INSTANCE!!
        }
    }



}