package com.android.rideway_app.map

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.rideway_app.R
import com.android.rideway_app.databinding.ActivityCourseBinding
import com.android.rideway_app.databinding.ActivityCourseDetailBinding
import com.android.rideway_app.mainapp.MainApplication
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.fileUpload.FileService
import com.android.rideway_app.retrofit.fileUpload.GpxResponseData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class CourseDetailActivity : AppCompatActivity() , OnMapReadyCallback {
    private lateinit var binding : ActivityCourseDetailBinding
    private var courseBoardId : Long = 0
    private var courseId : Long = 0

    lateinit var gpxData : List<List<Double>>

    private val route = PathOverlay()
    private lateinit var naverMap: NaverMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        courseBoardId = intent.getLongExtra("courseBoardId",0)
        NaverMapSdk.getInstance(this@CourseDetailActivity).client = NaverMapSdk.NaverCloudPlatformClient("is58t3w4qp")
        getDetail()


        val options = NaverMapOptions()
            .camera(CameraPosition(LatLng(35.1798159, 129.0750222), 8.0))
            .mapType(NaverMap.MapType.Basic)

        val fm = supportFragmentManager

        val mapFragment = fm.findFragmentById(R.id.course_map) as MapFragment?
            ?: MapFragment.newInstance(options).also {
                fm.beginTransaction().add(R.id.course_map, it).commit()
            }

        mapFragment.getMapAsync(this)


        binding.btnNoZzim.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch{
                setMyCourse()
                checkCourse()
            }

        }
        binding.btnZzim.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch{
                delMyCourse()
                checkCourse()
            }
        }

        binding.goComment.setOnClickListener {
            val intent = Intent(this,CourseCommentActivity::class.java)
            intent.putExtra("courseBoardId",courseBoardId)
            startActivity(intent)
        }
    }

    private suspend fun setMyCourse(){
        val data = hashMapOf<String,String>()
        data.put("user_id",MainApplication.getUserId().toString())
        data.put("course_id",courseId.toString())
        RetrofitClient.getInstance().create(FileService::class.java).setMyCourse(data)
    }
    private suspend fun delMyCourse(){
        RetrofitClient.getInstance().create(FileService::class.java).delMyCourse(MainApplication.getUserId().toLong(),courseId)
    }
    private fun getDetail(){
        CoroutineScope(Dispatchers.IO).launch {
            val content = async {
                RetrofitClient.getInstance().create(FileService::class.java).getCourseDetail(courseBoardId,MainApplication.getUserId().toLong())
            }
            launch(Dispatchers.Main) {
                val get = content.await()
                courseId = get.courseId.courseId.toLong()
                binding.title.text = get.title
                binding.content.setHtml(get.content)
                binding.writeDay.text = get.regTime
                MainApplication.setOtherUserProfilePK(get.userId.toLong(),binding.ciProfileImage)
                binding.userName.text = get.userNickname
                binding.visited.text = "조회수 : ${get.visited}"
                binding.commentCount.text = "(${get.comment.size})"
                checkCourse()
                getGpxData(courseId)
            }
        }
    }

    private suspend fun checkCourse() {
        val check = RetrofitClient.getInstance().create(FileService::class.java).checkCustomCourse(MainApplication.getUserId().toLong(),courseId)
        if(check){
            binding.btnNoZzim.visibility = View.GONE
            binding.btnZzim.visibility = View.VISIBLE
        }else{
            binding.btnNoZzim.visibility = View.VISIBLE
            binding.btnZzim.visibility = View.GONE
        }
    }
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        val uiSettings = naverMap.uiSettings
        uiSettings.isCompassEnabled = false
    }
    private fun makingPath(){
        var mutableList = mutableListOf<LatLng>()

        for(i in gpxData.indices){
            mutableList.add(LatLng(gpxData[i][0], gpxData[i][1]))
        }
        route.coords = mutableList
        route.patternImage = OverlayImage.fromResource(R.drawable.path_pattern)
        route.patternInterval = 60
        route.outlineWidth = 0
        route.width = 25
        route.color = Color.BLUE
        route.map = naverMap
        naverMap.cameraPosition = CameraPosition(LatLng(gpxData[0][0], gpxData[0][1]), 14.0)
    }

    // GPX 파일의 데이터를 가져오는 메소드
    private fun getGpxData(course_id : Long){
        val retrofitAPI = RetrofitClient.getInstance().create(FileService::class.java)
        retrofitAPI.getRoute(course_id).enqueue(object : Callback<GpxResponseData> {
            override fun onResponse(call: Call<GpxResponseData>, response: Response<GpxResponseData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        gpxData = response.body()!!

                        if (gpxData.size<2) Toast.makeText(this@CourseDetailActivity, "오류! gpx파일이 손상되어있습니다.", Toast.LENGTH_LONG).show()
                        else makingPath()
                    }
                }
                else{}
            }
            override fun onFailure(call: Call<GpxResponseData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}