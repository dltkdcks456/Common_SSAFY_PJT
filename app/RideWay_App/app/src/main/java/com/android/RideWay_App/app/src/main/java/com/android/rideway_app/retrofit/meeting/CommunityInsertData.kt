package com.android.rideway_app.retrofit.meeting

data class CommunityInsertData(
    val token: String,
    val title : String,
    val content : String,
    val si : String,
    val gun : String,
    val dong : String,
    val max_person : String,
    val start_time : String,
    val in_progress : Boolean,
    val community_id : String
)