package com.android.rideway_app.retrofit.login

import android.provider.ContactsContract.CommonDataKinds.Nickname
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface LoginService {
    //로그인
    @POST("user/login")
    fun getLoginData(@Body loginData : LoginData) : Call<LoginDataResponse>

    @GET("user/signup/id")
    fun getIdCheck(@Query("id") id : String) : Call<Boolean>

    @GET("user/registerMail")
    fun getEmail(@Query("email") email : String) : Call<Boolean>

    @GET("user/certMail")
    fun getCertCode(@Query("code") code : String) : Call<Boolean>

    @GET("user/signup/nickname")
    fun getNickNameCode(@Query("nickname") code : String) : Call<Boolean>

    @POST("user/findPassword")
    fun getNewPassword(@Body passSearchData : PassSearchData) : Call<StringResultResponseData>

    @POST("user/findId")
    fun getId(@Body idSearchData : IdSearchData) : Call<IdSearchDataResponse>

    @POST("user/signup")
    fun signupRequest(@Body signUpData: SignUpData) : Call<StringResultResponseData>

    @POST("user/getUserInfo")
    fun getMyProfile(@Body token: TokenData) : Call<UserProfileData>

    @PUT("user/edit")
    fun modifyMyProfile(@Body modifyUserProfileData: ModifyUserProfileData) : Call<ModifyUserProfileData>

    @PUT("user/editpwd")
    fun modifyMyPassword(@Body passModifyData: PassModifyData) : Call<StringResultResponseData>

    @HTTP(method = "DELETE", path="user/deleteUser", hasBody = true)
    fun deleteAccount(@Body deleteAccountData: DeleteAccountData) : Call<StringResultResponseData>

    @PUT("user/editEmail")
    fun modifyMyEmail(@Body emailModifyData: EmailModifyData) : Call<StringResultResponseData>
    
    //유저 검색
    @GET("user/search/nickname")
    suspend fun searchUser(@Query("nickname") nickname: String) : List<UserDataResponse>


    @GET("user/imageDownload")
    suspend fun downloadImage(@Query("id") id:String): ResponseBody

    @GET("user/imageDownloadBy/{path}")
    suspend fun downloadImageBy(@Path("path",encoded = true) path : String): ResponseBody

    @GET("user/imageDownloadPK")
    suspend fun downloadImagePK(@Query("id") id : Long): ResponseBody

    @GET("user/search")
    fun searchOneUser(@Query("nickname") nickname: String) : Call<OneUserDataResponse>
}