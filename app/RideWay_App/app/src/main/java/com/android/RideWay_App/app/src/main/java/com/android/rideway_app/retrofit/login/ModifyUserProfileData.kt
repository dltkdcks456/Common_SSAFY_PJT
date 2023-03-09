package com.android.rideway_app.retrofit.login

// 자전거 무게는 회원가입 data클래스랑 여기가 int형으로 바뀌어야함
data class ModifyUserProfileData(
    val email : String,
    val nickname : String,
    val image_path : String,
    val open : String,
    val si : String,
    val gun : String,
    val dong : String,
    val weight : String,
    val cycle_weight : String,
    val token : String
)
