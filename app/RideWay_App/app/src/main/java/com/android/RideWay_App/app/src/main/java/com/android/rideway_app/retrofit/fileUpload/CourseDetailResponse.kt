package com.android.rideway_app.retrofit.fileUpload

data class CourseDetailResponse(
    val comment: List<Comment>,
    val content: String,
    val courseBoardId: Int,
    val courseId: CourseId,
    val hateCount: Int,
    val likeCount: Int,
    val regTime: String,
    val title: String,
    val userId: Int,
    val userNickname: String,
    val visited: Int
) {
    data class Comment(
        val content: String,
        val courseBoardCommentId: Int,
        val courseBoardId: Int,
        val time: String,
        val userId: Int,
        val userNickname: String
    )

    data class CourseId(
        val courseId: Int,
        val name: String,
        val path: String,
        val title: String,
        val type: String,
        val userId: Int
    )
}