package com.android.rideway_app.map

import android.app.Activity
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.android.rideway_app.databinding.ActivityCourseFileSaveBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.fileUpload.FileService
import com.android.rideway_app.retrofit.fileUpload.GpxNameChangeData
import com.android.rideway_app.retrofit.fileUpload.MyCoursePostData
import com.android.rideway_app.retrofit.fileUpload.PostBoardCourseData
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream

class CourseFileSaveActivity : AppCompatActivity() {

    lateinit var binding : ActivityCourseFileSaveBinding
    var uploaded = false
    var course_id : Long = -10
    private lateinit var userProfile: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseFileSaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)
        binding.btnGpxUploadServer.isEnabled = false

        binding.ibBack.setOnClickListener {
            AlertDialog.Builder(this@CourseFileSaveActivity)
                .setTitle("저장 취소")
                .setMessage("\nGPX 저장을 그만두시겠습니까?")
                .setPositiveButton("네") { _, _ -> finish()}
                .setNegativeButton("아니오"){_,_ -> }
                .setCancelable(false)
                .create()
                .show()
        }

        binding.etGpxName.addTextChangedListener {
            binding.btnGpxUploadServer.isEnabled = !binding.etGpxName.text.isEmpty()
        }

        binding.btnGpxUploadServer.setOnClickListener {
            postGpxFile()
        }

    }


    // 내 GPX 파일을 업로드 하는 부분
    private fun postGpxFile(){
        val retrofitAPI = RetrofitClient.getInstance().create(FileService::class.java)

        if(uploaded && course_id>0){
            setGpxFileName(course_id)
        }else{

            val gpxFile = File(filesDir.absolutePath + "/gpx/gpxFile.gpx")
            val requestBody = RequestBody.create(MediaType.parse("gpx/gpx"), gpxFile)
            val fileToUpload: MultipartBody.Part = MultipartBody.Part.createFormData("gpxFile", gpxFile.name, requestBody)

            // user id 와 같이
            retrofitAPI.postGpxFile(userProfile.getInt("user_id", -1).toLong(), fileToUpload).enqueue(object :
                Callback<Long> {
                override fun onResponse(call: Call<Long>, response: Response<Long>) {
                    if(response.isSuccessful){
                        if(response.body() != null){
                            uploaded = true
                            course_id = response.body()!!
                            if(course_id>0){
                                postMyCourseBoard(course_id)
                                setGpxFileName(course_id)
                            }
                            else{
                                AlertDialog.Builder(this@CourseFileSaveActivity)
                                    .setTitle("업로드 실패")
                                    .setMessage("\n네트워크에 문제가 발생하였습니다.\n다시 시도해주세요")
                                    .setPositiveButton("확인") { _, _ -> }
                                    .create()
                                    .show()
                            }
                        }
                    }
                    else{
                        AlertDialog.Builder(this@CourseFileSaveActivity)
                            .setTitle("업로드 실패")
                            .setMessage("\n네트워크에 문제가 발생하였습니다.\n다시 시도해주세요")
                            .setPositiveButton("확인") { _, _ -> }
                            .create()
                            .show()
                    }
                }
                override fun onFailure(call: Call<Long>, t: Throwable) {
                    AlertDialog.Builder(this@CourseFileSaveActivity)
                        .setTitle("업로드 완료")
                        .setMessage("\n네트워크에 문제가 발생하였습니다.\n다시 시도해주세요")
                        .setPositiveButton("확인") { _, _ -> }
                        .create()
                        .show()
                }
            })

        }

    }


    // 내 GPX 파일을 업로드 하는 부분
    private fun setGpxFileName(course_id : Long){
        val retrofitAPI = RetrofitClient.getInstance().create(FileService::class.java)

        // user id 와 같이
        retrofitAPI.putGpxName(GpxNameChangeData(course_id, binding.etGpxName.text.toString())).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    if(response.body() != null){

                        AlertDialog.Builder(this@CourseFileSaveActivity)
                            .setTitle("업로드 완료")
                            .setMessage("\n기록이 성공적으로 업로드 되었습니다!")
                            .setPositiveButton("확인") { _, _ -> }
                            .create()
                            .show()

                    }
                }
                else{
                    AlertDialog.Builder(this@CourseFileSaveActivity)
                        .setTitle("업로드 실패")
                        .setMessage("\n네트워크에 문제가 발생하였습니다.\n다시 시도해주세요")
                        .setPositiveButton("확인") { _, _ -> }
                        .create()
                        .show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                AlertDialog.Builder(this@CourseFileSaveActivity)
                    .setTitle("업로드 완료")
                    .setMessage("\n네트워크에 문제가 발생하였습니다.\n다시 시도해주세요")
                    .setPositiveButton("확인") { _, _ -> }
                    .create()
                    .show()
            }
        })
    }

    private fun postMyCourseBoard(course_id : Long){

        val retrofitAPI = RetrofitClient.getInstance().create(FileService::class.java)

        retrofitAPI.postMyCourse(MyCoursePostData(userProfile.getInt("user_id",-1).toLong(), course_id)).enqueue(
            object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.isSuccessful){

                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                }
            })

        retrofitAPI.postCourseBoard(PostBoardCourseData(userProfile.getInt("user_id",-1).toLong(), course_id,
            binding.etGpxName.text.toString(), binding.ettmContent.text.toString())).enqueue(
            object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.isSuccessful){

                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                }
            })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder(this@CourseFileSaveActivity)
                .setTitle("저장 취소")
                .setMessage("\nGPX 저장을 그만두시겠습니까?")
                .setPositiveButton("네") { _, _ -> finish()}
                .setNegativeButton("아니오"){_,_ -> }
                .setCancelable(false)
                .create()
                .show()
            return true
        }

        return false
    }

}