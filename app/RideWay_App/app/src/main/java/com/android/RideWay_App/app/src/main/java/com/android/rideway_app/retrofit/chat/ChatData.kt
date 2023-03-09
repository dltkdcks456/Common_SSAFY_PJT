package com.android.rideway_app.retrofit.chat

data class ChatData(
    val sender : String,
    val messageType : String,
    val roomId : String,
    val message : String,
)
