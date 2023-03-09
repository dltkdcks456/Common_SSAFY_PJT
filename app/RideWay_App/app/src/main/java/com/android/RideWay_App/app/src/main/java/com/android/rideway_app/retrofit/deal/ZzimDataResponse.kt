package com.android.rideway_app.retrofit.deal

data class ZzimDataResponse(
    val dealPK: DealPK
) {
    data class DealPK(
        val dealId: Int,
        val userId: Int
    )
}