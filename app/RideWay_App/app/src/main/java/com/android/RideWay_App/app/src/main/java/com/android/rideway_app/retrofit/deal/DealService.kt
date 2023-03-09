package com.android.rideway_app.retrofit.deal

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface DealService {
    
    //거래 목록
    @GET("deal/app")
    fun getDealList(@Query("page") page : Int) : Call<DealListResponse>

    //거래 목록
    @GET("deal/search")
    fun getDealListWithSearch(@Query("page") page : Int,@Query("keyword") keyword:String) : Call<DealListResponse>

    //거래 상세
    @GET("deal/app/detail")
    fun getDealDetail(@Header("userId") userId : Long , @Query("dealId") dealId : Long) : Call<DealDetailResponse>

    //거래 상세 이미지
    @GET("deal/imageListDownload/")
    fun getDealImage(@Query("deal_id") dealId : Long) : Call<List<String>>

    //거래 상세 이미지
    @GET("deal/imageListDownload/")
    suspend fun getDealImageCoroutine(@Query("deal_id") dealId : Long) : List<String>

    //거래 등록
    @Multipart
    @POST("deal/app")
    fun insertDeal(
        @PartMap param: HashMap<String, RequestBody>,
        @Part images: List<MultipartBody.Part>,
    ): Call<Void>

    //거래 수정
    @Multipart
    @PUT("deal/app")
    fun updateDeal(
        @PartMap param: HashMap<String, RequestBody>,
        @Part images: List<MultipartBody.Part>,
    ) : Call<Void>

    //거래 삭제
    @DELETE("deal/app/{dealId}")
    fun deleteDeal(@Path("dealId") dealId : Long) : Call<Void>

    //찜 확인
    @GET("deal/zzimCheck")
    fun getMyFavAboutIt(@Query("dealId") dealId: Long, @Query("userId") userId: Long): Call<Boolean>

    //찜 추가
    @GET("deal/addZzim")
    fun addFav(@Query("dealId") dealId: Long , @Query("userId") userId: Long) : Call<Void>
    
    //찜 삭제
    @DELETE("deal/zzim/{dealId}")
    fun delFav(@Path("dealId") dealId: Long , @Header("userId") userId: Long) : Call<Void>

    //거래 완료
    @GET("soldOut")
    fun dealComplete(@Query("dealId") dealId: Long) : Call<Void>
}