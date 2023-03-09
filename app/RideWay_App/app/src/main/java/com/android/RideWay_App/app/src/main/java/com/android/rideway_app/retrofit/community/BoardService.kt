package com.android.rideway_app.retrofit.community

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface BoardService {

    @GET("board/code")
    fun getBoardCode() : Call<List<BoardKind>>

    @GET("board/code")
    suspend fun getBoardCodeCoroutine() : List<BoardKind>

    @GET("board/{board_code}")
    fun getBoardList(@Path("board_code") board_code : Int , @Query("page") page : Int) : Call<BoardDataResponse>

    @GET("board/{board_code}")
    suspend fun getBoardListCoroutine(@Path("board_code") board_code : Int , @Query("page") page : Int,@Query("size") size:Int = 5) : BoardDataResponse

    @GET("board/search")
    fun getBoardSearchList(@Query("boardCode") board_code : Int ,@Query("keyword") keyword : String , @Query("page") page : Int) : Call<BoardDataResponse>

    @GET("board/{board_code}/")
    fun getBoardDetail(@Header("userId") userId : Int ,@Path("board_code") board_code : Int , @Query("boardId") board_id : Int) : Call<BoardDetailResponse>

    @Multipart
    @POST("board/app")
    fun insertBoard(
        @PartMap param : HashMap<String,RequestBody>,
        @Part images : List<MultipartBody.Part>
    ) : Call<Void>
    @Multipart
    @PUT("board/app")
    fun updateBoard(
        @PartMap param : HashMap<String,RequestBody>,
        @Part images : List<MultipartBody.Part>
    ) : Call<Void>

    @DELETE("board/app/{boardId}")
    fun deleteBoard(@Path("boardId") board_id : Int) : Call<Void>

    @POST("board/comment/")
    suspend fun insertComment(@Body param : HashMap<String,String>)

    @PUT("board/comment/")
    suspend fun updateComment(@Body param : HashMap<String,String>)

    @DELETE("board/comment/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId : String)
}