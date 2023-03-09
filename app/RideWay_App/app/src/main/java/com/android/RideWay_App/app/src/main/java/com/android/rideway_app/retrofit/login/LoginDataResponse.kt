package com.android.rideway_app.retrofit.login

data class LoginDataResponse(
    val user_id: Int,
    val age: String,
    val dong: String,
    val email: String,
    val gun: String,
    val image_path: String,
    val name: String,
    val nickname: String,
    val permission: Int,
    val si: String,
    val token: String,
    val weight: Int,
    val cycle_weight : Int
)