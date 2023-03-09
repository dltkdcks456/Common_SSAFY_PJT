package com.android.rideway_app.mainapp

import android.app.Application
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.login.LoginService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.InputStream

//전역 저장소
class MainApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
        fun getUserId() : Int {
            return prefs.getUserId()
        }
        fun getUserToken() : String{
            return prefs.getUserToken()
        }

        fun setOtherUserProfile(id : String , iv : ImageView){
            CoroutineScope(Dispatchers.IO).launch{
                val byteStream = async {
                    getImageBmp(id)
                }
                launch (Dispatchers.Main){
                    val bmp = BitmapFactory.decodeStream(byteStream.await())
                    iv.setImageBitmap(bmp)
                }
            }
        }
        private suspend fun getImageBmp(id : String) : InputStream {
            val retrofitAPI = RetrofitClient.getInstance().create(LoginService::class.java)
            return retrofitAPI.downloadImage(id).byteStream()
        }

        fun setOtherUserProfileBy(path : String , iv : ImageView){
            CoroutineScope(Dispatchers.IO).launch{
                val byteStream = async {
                    getImageBmpBy(path)
                }
                launch (Dispatchers.Main){
                    val bmp = BitmapFactory.decodeStream(byteStream.await())
                    iv.setImageBitmap(bmp)
                }
            }
        }
        private suspend fun getImageBmpBy(path : String) : InputStream {
            val retrofitAPI = RetrofitClient.getInstance().create(LoginService::class.java)
            return retrofitAPI.downloadImageBy(path).byteStream()
        }

        fun setOtherUserProfilePK(id : Long , iv : ImageView){
            CoroutineScope(Dispatchers.IO).launch{
                val byteStream = async {
                    getImageBmpPK(id)
                }
                launch (Dispatchers.Main){
                    val bmp = BitmapFactory.decodeStream(byteStream.await())
                    iv.setImageBitmap(bmp)
                }
            }
        }
        private suspend fun getImageBmpPK(id : Long) : InputStream {
            val retrofitAPI = RetrofitClient.getInstance().create(LoginService::class.java)
            return retrofitAPI.downloadImagePK(id).byteStream()
        }
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}