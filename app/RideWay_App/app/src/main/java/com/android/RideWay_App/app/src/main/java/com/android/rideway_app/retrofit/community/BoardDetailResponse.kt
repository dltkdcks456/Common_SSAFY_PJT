package com.android.rideway_app.retrofit.community

data class BoardDetailResponse(
    val boardCode: Int,
    val boardId: Int,
    val comment: List<Comment>,
    val content: String,
    val count: Int,
    val hateCount: Int,
    val likeCount: Int,
    val regTime: String,
    val title: String,
    val userId: Int,
    val userNickname: String,
    val visited: Int
) {
    data class Comment(
        val boardId: Int,
        val commentId: Int,
        val content: String,
        val time: String,
        val userId: Int,
        val userNickname: String
    )
}