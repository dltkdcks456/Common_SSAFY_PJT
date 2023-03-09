package com.android.rideway_app.retrofit.meeting

data class CommunityListResponse(
    val content: List<Content>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: Sort,
    val totalElements: Int,
    val totalPages: Int
) {
    data class Content(
        val communityId: Int,
        val currentPerson: Int,
        val date: String,
        val dong: String,
        val gun: String,
        val inProgress: Boolean,
        val maxPerson: Int,
        val si: String,
        val title: String,
        val userId: String,
        val userNickname: String
    )

    data class Pageable(
        val offset: Int,
        val pageNumber: Int,
        val pageSize: Int,
        val paged: Boolean,
        val sort: Sort,
        val unpaged: Boolean
    ) {
        data class Sort(
            val empty: Boolean,
            val sorted: Boolean,
            val unsorted: Boolean
        )
    }

    data class Sort(
        val empty: Boolean,
        val sorted: Boolean,
        val unsorted: Boolean
    )
}