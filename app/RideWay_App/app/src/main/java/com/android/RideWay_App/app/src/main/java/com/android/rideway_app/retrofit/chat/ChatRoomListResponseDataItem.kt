package com.android.rideway_app.retrofit.chat

data class ChatRoomListResponseDataItem(
    val alarm: Boolean,
    val chattingRoomId: String,
    val name: String,
    val type: Int
)