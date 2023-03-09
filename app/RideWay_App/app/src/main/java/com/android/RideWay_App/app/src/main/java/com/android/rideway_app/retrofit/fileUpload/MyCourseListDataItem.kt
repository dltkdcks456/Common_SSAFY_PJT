package com.android.rideway_app.retrofit.fileUpload

data class MyCourseListDataItem(
    val courseId: Int,
    val like: Int,
    val regTime: String,
    val title: String,
    val userId: Int,
    val visited: Int
)