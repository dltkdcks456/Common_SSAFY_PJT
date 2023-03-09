package com.android.rideway_app.retrofit.meeting

import retrofit2.Call
import retrofit2.http.*

interface CommunityService {

    //리스트
    @GET("community/")
    fun getList(@Query("page") page: Int): Call<CommunityListResponse>

    @GET("community/")
    suspend fun getListCoroutine(@Query("page") page: Int,@Query("size") size : Int = 5): CommunityListResponse

    //검색
    @GET("community/searchRoom")
    fun getListWithSearch(@Query("page") page : Int ,
                          @Query("si") si : String,
                          @Query("gun") gun : String,
                          @Query("dong") dong : String,
                          @Query("search_word") keyword : String) : Call<CommunityListResponse>

    //상세
    @GET("community/info")
    fun getDetail(@Query("community_id") communityId: String): Call<CommunityDetailResponse>

    //등록
    @POST("community/")
    fun insertCommunity(@Body param : CommunityInsertData) : Call<Void>

    //수정
    @PUT("community/")
    fun updateCommunity(@Body param: CommunityInsertData) : Call<Void>

    //삭제
    @HTTP(method = "DELETE", path="community/", hasBody = true)
    fun deleteCommunity(@Body param : HashMap<String,String>) : Call<Void>

    //유저 정보
    @GET("community/usersInfo")
    fun getCommunityUsers(@Query("communityId") communityId: String) : Call<List<ParticipantDataResponse>>

    //유저가 커뮤니티에 속해있는지 확인
    @GET("community/check/{community_id}")
    fun checkUser(@Path("community_id") communityId: String) : Call<Boolean>

    @GET("community/check/{community_id}")
    suspend fun checkUserCoroutine(@Path("community_id") communityId: String) : Boolean

    //신청
    @PUT("community/addPerson")
    fun addUser(@Body data : SignUpData) : Call<Void>

    //탈퇴
    @HTTP(method = "DELETE", path="community/removePerson", hasBody = true)
    fun delUser(@Body data : SignUpData) : Call<Void>

    //밴유저 리스트
    @GET("community/showBanList")
    fun banList(@Header("community_id") communityId: String) : Call<List<String>>

    //강퇴 & 밴
    @POST("community/banPerson")
    fun banUser(@Body data : HashMap<String,String>) : Call<Void>

    //밴 해제
    @HTTP(method = "DELETE", path="community/cancelBanPerson", hasBody = true)
    fun banCancelUser(@Body data : HashMap<String,String>) : Call<Void>

    //초대
    @PUT("community/invitePerson")
    fun inviteUser(@Body data : HashMap<String,String>) : Call<Void>
}