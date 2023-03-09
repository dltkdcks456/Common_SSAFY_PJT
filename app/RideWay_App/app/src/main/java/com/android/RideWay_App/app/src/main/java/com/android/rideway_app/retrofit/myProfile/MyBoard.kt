package com.android.rideway_app.retrofit.myProfile

data class MyBoard(
    val board_code: Int,
    val board_code_name: String,
    val board_content: String,
    val board_hate_count: Int,
    val board_id: Int,
    val board_like_count: Int,
    val board_reg_time: String,
    val board_title: String,
    val board_visited: Int
)