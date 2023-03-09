package com.android.rideway_app.retrofit.weeklyUser

import com.android.rideway_app.retrofit.login.OneUserDataResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface WeeklyService {

//    @GET("recode/getBestWeekTime")
//    fun getBestTime() : Call<WeeklyTimeData>

    @GET("recode/getBestWeekTime")
    suspend fun getBestTime() : WeeklyTimeData

    @GET("recode/getBestWeekDist")
    suspend fun getBestDist() : WeeklyDistData

    @GET("recode/getBestWeekCal")
    suspend fun getBestCal() : WeeklyCalData

    @GET("recode/getUserInfo")
    fun getUserRecord(@Query("nickname") nickname : String) : Call<UserRecordData>

    @GET("user/search")
    suspend fun searchOneUser(@Query("nickname") nickname: String) : OneUserDataResponse

    @GET("recode/getUserInfo")
    suspend fun getWeekUserRecord(@Query("nickname") nickname : String) : UserRecordData

    @GET("user/imageDownloadPK")
    suspend fun downloadImagePk(@Query("id") id:Long):ResponseBody;

}