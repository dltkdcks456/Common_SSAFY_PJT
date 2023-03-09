package com.android.rideway_app.retrofit.meeting

data class CommunityDetailResponse(
    val chatting_room_id: String,
    val content: String,
    val current_person: Int,
    val date: String,
    val dong: String,
    val gun: String,
    val in_progress: Boolean,
    val max_person: Int,
    val si: String,
    val title: String
)