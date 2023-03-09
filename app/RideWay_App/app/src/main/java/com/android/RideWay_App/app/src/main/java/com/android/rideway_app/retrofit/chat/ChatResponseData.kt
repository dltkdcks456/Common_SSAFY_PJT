package com.android.rideway_app.retrofit.chat

import java.time.LocalDateTime

data class ChatResponseData(
    val sender : Long,
    val messageType : Int,
    val senderNickName : String,
    val roomId : String,
    val message : String,
    val sendTime : String
)
