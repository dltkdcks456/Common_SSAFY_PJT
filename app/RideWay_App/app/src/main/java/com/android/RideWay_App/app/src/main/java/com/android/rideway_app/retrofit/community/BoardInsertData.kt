package com.android.rideway_app.retrofit.community

data class BoardInsertData(
    val user_id : Int,
    val title : String,
    val content : String,
    val board_code : String,
    val token : String,
    val imgMap : Map<String,Int>
)