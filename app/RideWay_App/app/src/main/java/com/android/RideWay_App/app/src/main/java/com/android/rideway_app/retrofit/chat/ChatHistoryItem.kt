package com.android.rideway_app.retrofit.chat

data class ChatHistoryItem(
    val message: String,
    val messageType: Int,
    val sendTime: String,
    val sender_id: Long,
    val sender_nickname: String
)