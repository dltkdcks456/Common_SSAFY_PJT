package com.android.rideway_app.retrofit

class TestDataResponse : ArrayList<TestDataResponse.TestDataResponseItem>(){
    data class TestDataResponseItem(
        val body: String,
        val id: Int,
        val title: String,
        val userId: Int
    )
}