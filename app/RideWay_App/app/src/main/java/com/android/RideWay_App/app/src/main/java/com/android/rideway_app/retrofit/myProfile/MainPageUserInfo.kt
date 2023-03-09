package com.android.rideway_app.retrofit.myProfile

data class MainPageUserInfo(
    val event : String,
    val nickname : String,
    val image_path: String,
    val weeklyData : Int,
    val total_time : Int,
    val address : String,
    val id : Long,
)
