package com.android.rideway_app.retrofit.myProfile

import com.android.rideway_app.retrofit.login.TokenData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface MyProfileService {
    //로그인
    @POST("achievement/update")
    fun updateMyAchievement(@Body tokenData: TokenData) : Call<ResponseBody>

    @GET("achievement/getUserAchievement")
    fun getMyAchievement(@Query("nickname") nickname : String) : Call<MyAchievementsData>

    @POST("recode/putUserRecode")
    fun postMyRecord(@Body myRecordData: MyRecordData) : Call<ResponseBody>

    @GET("mypage/zzimList")
    suspend fun getZzimList() : List<MyZzim>

    @GET("mypage/boardList")
    suspend fun getBoardList(@Query("nickname") nickname: String) : List<MyBoard>

}