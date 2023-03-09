package com.android.rideway_app.retrofit.fileUpload

import com.android.rideway_app.retrofit.login.TokenData
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface FileService {
    // 이미지 업로드
    @Multipart
    @POST("user/imageUpload")
    fun postImageFile(@Query("token") token: String, @Part imageFile :MultipartBody.Part) : Call<String>

    // 이미지 다운로드
    @GET("user/imageDownload")
    fun downloadImage(@Query("id") id:String):Call<ResponseBody>;

    @GET("user/imageDownloadPK")
    fun downloadImagePk(@Query("id") id:Long):Call<ResponseBody>;

    @Multipart
    @POST("course/custom/")
    fun postGpxFile(@Query("user_id") user_id: Long, @Part gpxFile :MultipartBody.Part) : Call<Long>

    @GET("course/custom/")
    fun getRoute(@Query("course_id") course_id:Long):Call<GpxResponseData>;

    @PUT("course/custom/title")
    fun putGpxName(@Body gpxNameChangeData: GpxNameChangeData):Call<ResponseBody>;

    @GET("course/custom/myCourse")
    fun getMyCourse(@Query("user_id") user_id: Long):Call<MyCourseListData>

    @POST("course/custom/myCourse")
    fun postMyCourse(@Body myCoursePostData: MyCoursePostData):Call<ResponseBody>

    @POST("course/")
    fun postCourseBoard(@Body postBoardCourseData: PostBoardCourseData):Call<ResponseBody>

    @POST("course/comment/")
    suspend fun insertComment(@Body param :HashMap<String,String>)

    @PUT("course/comment/")
    suspend fun updateComment(@Body param :HashMap<String,String>)

    @DELETE("course/comment/{courseBoardCommentId}")
    suspend fun deleteComment(@Path("courseBoardCommentId") id : Long)

    @POST("course/custom/myCourse")
    suspend fun setMyCourse(@Body param: HashMap<String, String>)

    @DELETE("course/custom/myCourse/{course_id}/{user_id}")
    suspend fun delMyCourse(@Path("user_id") userId: Long,@Path("course_id") courseId: Long)


    @GET("course/")
    suspend fun getCourseList(@Query("page") page : Int) : CourseListDataResponse

    @GET("course/{courseBoardId}")
    suspend fun getCourseDetail(@Path("courseBoardId") courseBoardId : Long,@Header("userId") userId : Long) : CourseDetailResponse

    @GET("course/custom/myCourseCheck")
    suspend fun checkCustomCourse(@Query("userId") userId : Long , @Query("courseId") courseId : Long) : Boolean

    @GET("course/search")
    suspend fun getCourseListWithSearch(@Query("page") page : Int , @Query("keyword") keyword : String) : CourseListDataResponse
}