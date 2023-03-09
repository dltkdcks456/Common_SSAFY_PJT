package com.android.rideway_app.retrofit.deal

data class DealDetailResponse(
    val content: String,
    val dealId: Int,
    val kind: String,
    val name: String,
    val onSale: Boolean,
    val price: Long,
    val time: String,
    val title: String,
    val userId: Int,
    val userNickname: String,
    val visited: Int
)