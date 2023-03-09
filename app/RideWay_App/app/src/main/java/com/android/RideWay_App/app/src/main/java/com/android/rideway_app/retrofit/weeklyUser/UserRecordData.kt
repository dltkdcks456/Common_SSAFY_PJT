package com.android.rideway_app.retrofit.weeklyUser

data class UserRecordData(
    val total_cal: Int,
    val total_dist: Int,
    val total_speed: Int,
    val total_time: Int,
    val week_cal: Int,
    val week_dist: Int,
    val week_speed: Int,
    val week_time: Int
)