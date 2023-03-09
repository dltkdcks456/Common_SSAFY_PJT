package com.android.rideway_app.retrofit.login

data class UserProfileData(
    val id : String,
    val password : String,
    val name : String,
    val email : String,
    val nickname : String,
    val permission : Int,
    val image_path : String,
    val cycle_weight : Int,
    val open : Boolean,
    val si : String,
    val gun : String,
    val dong : String,
    val weight : Int,
    val age : String,
    val gender : String,
)
