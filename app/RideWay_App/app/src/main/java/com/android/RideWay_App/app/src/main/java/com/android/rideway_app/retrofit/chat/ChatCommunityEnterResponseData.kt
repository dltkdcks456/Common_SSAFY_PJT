package com.android.rideway_app.retrofit.chat

data class ChatCommunityEnterResponseData(
    val alarm: Boolean,
    val chattingRoomId: String,
    val chattingRoomUserPk: Int,
    val recentChattingTime: Any,
    val userId: Int
)