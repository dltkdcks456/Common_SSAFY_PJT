package com.android.rideway_app.retrofit.chat

data class CreateChatRoomData(
    val token : String,
    val name : String,
    val opposite_nickname : String
)
