package com.android.rideway_app.map

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.*
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Chronometer.OnChronometerTickListener
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.rideway_app.R
import com.android.rideway_app.databinding.ActivityMapBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.chat.ChatHistory
import com.android.rideway_app.retrofit.chat.ChatService
import com.android.rideway_app.retrofit.fileUpload.FileService
import com.android.rideway_app.retrofit.fileUpload.GpxNameChangeData
import com.android.rideway_app.retrofit.fileUpload.GpxResponseData
import com.android.rideway_app.retrofit.fileUpload.MyCourseListData
import com.android.rideway_app.retrofit.myProfile.MyProfileService
import com.android.rideway_app.retrofit.myProfile.MyRecordData
import com.android.rideway_app.service.TestService
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding : ActivityMapBinding

    private lateinit var userProfile: SharedPreferences

    // gpxData값을 가져와서 받는 변수
    lateinit var gpxData : List<List<Double>>

    // 네이버 맵 변수
    private lateinit var naverMap: NaverMap

    // 현재 위치 가져오는 변수
    private lateinit var locationSource: FusedLocationSource

    // 플로팅 액션바 껏다 켰다 하기위해 있는 변수들
    private var isFabOpen = false
    private var bikeRoadOn = false
    private var centerMarkerOn = false
    private var unOfficialMarkerOn = false

    // 마커 찍는데 필요한 변수들
//    val centerMarker = Marker()
    val centerMarker = listOf<Marker>(
        Marker(LatLng(35.108029, 128.947841)), Marker(LatLng(35.311761, 128.976733)),
        Marker(LatLng(35.377156, 128.550396)), Marker(LatLng(35.592049, 128.360602)),
        Marker(LatLng(35.737142, 128.419003)), Marker(LatLng(35.841573, 128.462852)),
        Marker(LatLng(36.015198, 128.40073)), Marker(LatLng(36.23559, 128.350221)),
        Marker(LatLng(36.358304, 128.305729)), Marker(LatLng(36.431429, 128.250017)),
        Marker(LatLng(36.49884, 128.265678)), Marker(LatLng(36.577551,128.758665)),
        Marker(LatLng(36.65568, 128.140773)), Marker(LatLng(36.751621,128.032033)),
        Marker(LatLng(36.848328,	127.989626)), Marker(LatLng(36.989446,	127.903468)),
        Marker(LatLng(37.011143,	127.978768)), Marker(LatLng(37.11024,	127.809422)),
        Marker(LatLng(37.277174,	127.680917)), Marker(LatLng(37.326695,	127.60431)),
        Marker(LatLng(37.407027,	127.541634)), Marker(LatLng(37.497957,	127.485064)),
        Marker(LatLng(37.522196,	127.294977)), Marker(LatLng(37.54627,	127.120214)),
        Marker(LatLng(37.53355,	126.911678)), Marker(LatLng(37.598431,	126.800642)),
        Marker(LatLng(37.556823,	126.603924)), Marker(LatLng(36.767541, 128.003007)),
        Marker(LatLng(36.803416,	127.822508)), Marker(LatLng(36.787312,	127.582761)),
        Marker(LatLng(36.679358,	127.448206)), Marker(LatLng(36.517292,	127.321071)),
        Marker(LatLng(36.474299,	127.481658)), Marker(LatLng(36.476313,	127.259432)),
        Marker(LatLng(36.46043,	127.100321)), Marker(LatLng(36.31725,	126.942051)),
        Marker(LatLng(36.130985,	126.92196)), Marker(LatLng(36.020767,	126.764749)),
        Marker(LatLng(35.52073,	127.149567)), Marker(LatLng(35.460523,	127.201418)),
        Marker(LatLng(35.338839,	127.188119)), Marker(LatLng(35.303593,	127.327781)),
        Marker(LatLng(35.182024,	127.469578)), Marker(LatLng(35.184923,	127.62107)),
        Marker(LatLng(35.079308,	127.721754)), Marker(LatLng(34.959855,	127.761483)),
        Marker(LatLng(35.371322,127.018391)), Marker(LatLng(35.331901,	127.01683)),
        Marker(LatLng(35.248889,	126.893761)), Marker(LatLng(35.065293,	126.76164)),
        Marker(LatLng(34.970893,	126.62621)), Marker(LatLng(34.914775,	126.541685)),
        Marker(LatLng(34.802053,	126.445631)), Marker(LatLng(37.662378,	127.370517)),
        Marker(LatLng(37.553558,	127.312452)), Marker(LatLng(37.820858,	127.519528)),
        Marker(LatLng(37.920512,	127.713055)), Marker(LatLng(36.428543,	129.434438)),
        Marker(LatLng(36.600526,	129.411893)), Marker(LatLng(36.744001,	129.462005)),
        Marker(LatLng(36.872041,	129.42065)), Marker(LatLng(36.976958,	129.406964)),
        Marker(LatLng(37.222347,	129.339118)), Marker(LatLng(37.418899,	129.196021)),
        Marker(LatLng(37.476007,	129.159866)), Marker(LatLng(37.589959,	129.095066)),
        Marker(LatLng(37.687924,	129.037582)), Marker(LatLng(37.805228,	128.907414)),
        Marker(LatLng(37.918287,	128.808114)), Marker(LatLng(38.061985,	128.679524)),
        Marker(LatLng(38.213555, 128.600968)), Marker(LatLng(38.240456,	128.574961)),
        Marker(LatLng(38.400849,	128.477515)), Marker(LatLng(38.515178,	128.418208)),
        Marker(LatLng(33.515251,	126.511112)), Marker(LatLng(33.469401,	126.340245)),
        Marker(LatLng(33.370013,	126.206684)), Marker(LatLng(33.207067,	126.289657)),
        Marker(LatLng(33.23588,	126.515121)), Marker(LatLng(33.252266,	126.623377)),
        Marker(LatLng(33.325,	126.842342)), Marker(LatLng(33.468649,	126.924327)),
        Marker(LatLng(33.556975,	126.760182)), Marker(LatLng(33.542767,	126.672131)),

        )
    val unOfficialMarker = Marker()

    // 크로노미터 일시정지 값을 저장해두는 변수
    private var timeWhenStopped: Long = 0
    private var running = false

    // 크로노미터의 분이 10분 이상인지 확인하는 변수
    private var isOver10Min = false
    private var timeValue : Long = 0
    private var distValue : Int = 0
    private var calValue : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        NaverMapSdk.getInstance(this@MapActivity).client = NaverMapSdk.NaverCloudPlatformClient("is58t3w4qp")

        val options = NaverMapOptions()
            .camera(CameraPosition(LatLng(35.1798159, 129.0750222), 8.0))
            .mapType(NaverMap.MapType.Basic)

        val fm = supportFragmentManager

        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance(options).also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)

        binding.chronometer.onChronometerTickListener = OnChronometerTickListener {

            val time = SystemClock.elapsedRealtime() - it.base
            val h = (time / 3600000).toInt()
            val m = (time - h * 3600000).toInt() / 60000
            val s = (time - h * 3600000 - m * 60000).toInt() / 1000
            val t =
                (if (h < 10) "0$h" else h).toString() + ":" + (if (m < 10) "0$m" else m) + ":" + if (s < 10) "0$s" else s
            it.text = t
            if(!isOver10Min && m>=10) isOver10Min = true
            timeValue = time
        }
        binding.chronometer.text = "00:00:00"

    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        val uiSettings = naverMap.uiSettings
        val route = PathOverlay()
        val path = PathOverlay()

        uiSettings.isCompassEnabled = false

        // 마지막 좌표가 바뀔 때 마다 실행되는 함수
//        naverMap.addOnLocationChangeListener { location ->
//             Toast로 위도, 경도, 속도,
//            Toast.makeText(this, "${location.latitude}, ${location.longitude} ${location.time}" , Toast.LENGTH_SHORT).show()
//        }


        fun makingPath(){
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
            naverMap.cameraPosition = CameraPosition(LatLng(gpxData[0][0], gpxData[0][1]), 16.0)

        }

        // GPX 파일의 데이터를 가져오는 메소드
        fun getGpxData(course_id : Long){
            val retrofitAPI = RetrofitClient.getInstance().create(FileService::class.java)
            Log.d("오홋", "gpx파일 가져왔따리 " + course_id)
            retrofitAPI.getRoute(course_id).enqueue(object :
                Callback<GpxResponseData> {
                override fun onResponse(call: Call<GpxResponseData>, response: Response<GpxResponseData>) {

                    if(response.isSuccessful){
                        if(response.body() != null){
                            gpxData = response.body()!!
                            if (gpxData.size<2) Toast.makeText(this@MapActivity, "오류! gpx파일이 손상되어있습니다.", Toast.LENGTH_LONG).show()
                            else makingPath()
                        }
                    }
                    else{}
                }
                override fun onFailure(call: Call<GpxResponseData>, t: Throwable) {
                }
            })
        }

        fun getMyCourses(){

            val retrofitAPI = RetrofitClient.getInstance().create(FileService::class.java)

            retrofitAPI.getMyCourse(userProfile.getInt("user_id",-1).toLong()).enqueue(object : Callback<MyCourseListData> {
                override fun onResponse(call: Call<MyCourseListData>, response: Response<MyCourseListData>) {
                    Log.d("아라라랏", "ㅊㅊㅌㅋㅊㅌㅋ?")
                    if(response.isSuccessful){

                        Log.d("아라라랏", "ㅁㅁㄴㅇㅇㄴㅁ?")

                        var titleList : MutableList<String> = mutableListOf()

                        for(index in 0 until response.body()!!.size) titleList.add("경로 이름 : ${response.body()!![index].title}")
                        val array = titleList.toTypedArray()


                        AlertDialog.Builder(this@MapActivity).setTitle("list").setItems(array
                        ) { dialog, which ->
                            getGpxData( response.body()!![which].courseId.toLong())
                        }.show()

                    }
                }
                override fun onFailure(call: Call<MyCourseListData>, t: Throwable) {
                }
            })
        }



        // 주행 기록하기
        binding.btnRecord.setOnClickListener {

            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.text = "00:00:00"
            binding.chronometer.start()
            binding.btnRecord.visibility = View.GONE
            binding.btnMyCourseUpload.visibility = View.VISIBLE
            binding.tvMapDistance.text = "0km"
            binding.tvMapSpeed.text = "0km/h"
            binding.tvMapKCal.text = "0.0kcal"
//            getGpxData()
            start()
        }

        // 주행을 멈추고 경로 업로드 할지 물어보기
        binding.btnMyCourseUpload.setOnClickListener {

            binding.btnMyCourseUpload.visibility = View.GONE
            binding.btnRecord.visibility = View.VISIBLE
            end()

            var time = (timeValue).toInt() / 1000
            if(time == 0) time = 1
            postMyRecord(MyRecordData(userProfile.getString("token", null)!!, distValue, calValue, time))
            userProfile.edit().putInt("todayTime", userProfile.getInt("todayTime",0) + time).apply()
            userProfile.edit().putInt("todayDist", userProfile.getInt("todayDist",0) + distValue).apply()
            userProfile.edit().putInt("todayCal", userProfile.getInt("todayCal",0) + calValue).apply()

//            if(!isOver10Min) {
//
//                var time = (timeValue).toInt() / 1000
//                if(time == 0) time = 1
//                postMyRecord(MyRecordData(userProfile.getString("token", null)!!, distValue, calValue, time))
//
////                AlertDialog.Builder(this@MapActivity)
////                    .setTitle("고생하셨어요! 좋은 라이딩이었나요?")
////                    .setMessage("\n주행 결과 경로를 저장하시겠나요?")
////                    .setPositiveButton("네") { _, _ ->
////                        val intent = Intent(this@MapActivity, CourseFileSaveActivity::class.java)
////                        startActivity(intent)
////                    }
////                    .setNegativeButton("아니오"){_,_ -> }
////                    .setCancelable(false)
////                    .create()
////                    .show()
//            }
//            else{
//                AlertDialog.Builder(this@MapActivity)
//                    .setTitle("주행 종료")
//                    .setMessage("\n주행 기록은 최소 10분이상\n주행시 가능합니다.")
//                    .setPositiveButton("확인") { _, _ -> }
//                    .create()
//                    .show()
//            }

        }

        // 경로 가져오기
        binding.btnRoute.setOnClickListener {

            getMyCourses()


            /* 이 부분은 백엔드에서 마이 코스의 값들을 전해주면 여기에 대입만 하면 된다. */
//            data class Result(var courseTitle : String, var author : String ,var course_id : Long)
//            var list : MutableList<Result> = mutableListOf()
//            var titleList : MutableList<String> = mutableListOf()
//
//            list.add(Result("첫 코스", "작성자1", 1))
//            list.add(Result("두번째 코스", "작성자2", 2))
//            list.add(Result("세번째 코스", "작성자3", 3))
//
//            for(index in 0..list.size-1) titleList.add("경로 이름 : ${list[index].courseTitle} 작성자 : 누구게?")
//            val array = titleList.toTypedArray()
//
//
//            AlertDialog.Builder(this).setTitle("list").setItems(array
//            ) { dialog, which ->
//                getGpxData((which+1).toLong())
//                Toast.makeText(this@MapActivity, "정답! : ${list[which].author}", Toast.LENGTH_LONG).show()
//            }.show()

        }

        // 플로팅 액션 메인 버튼
        binding.fabMain.setOnClickListener {
            toggleFab()
        }

        // gps 플로팅 버튼
        binding.fabBtnGps.setOnClickListener {
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
        }

        // 플로팅 액션 자전거 지도 보여주기
        binding.fabBikeRoad.setOnClickListener {
            if(bikeRoadOn){
                bikeRoadOn = false
                naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BICYCLE, false)
            }
            else{
                bikeRoadOn = true
                naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BICYCLE, true)
            }
        }

        // 자전거 센터 보이기
        binding.fabBikeCenter.setOnClickListener {

            if(centerMarkerOn){
                centerMarkerOn = false
                for(i in centerMarker){
                    i.map = null
                }

            }else{
                centerMarkerOn = true
                for(i in centerMarker){
                    i.icon = MarkerIcons.GREEN
                    i.map = naverMap
                }

//                centerMarker.icon = MarkerIcons.BLUE
//                centerMarker.map = naverMap
            }

        }


        // 비공식 정보 보이기
        binding.fabUnofficialInformation.setOnClickListener {
            if(unOfficialMarkerOn){
                unOfficialMarkerOn= false
                unOfficialMarker.map = null

            }else{
                unOfficialMarkerOn= true
                unOfficialMarker.position = LatLng(35.0890135, 128.8493740)
                unOfficialMarker.icon = MarkerIcons.RED
                unOfficialMarker.map = naverMap
            }
        }

        naverMap.locationSource = locationSource

    }

    private fun postMyRecord( myRecordData: MyRecordData){
        val retrofitAPI = RetrofitClient.getInstance().create(MyProfileService::class.java)

        retrofitAPI.postMyRecord(myRecordData).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){

                    AlertDialog.Builder(this@MapActivity)
                        .setTitle("고생하셨어요! 좋은 라이딩이었나요?")
                        .setMessage("\n주행 결과 경로를 저장하시겠나요?")
                        .setPositiveButton("네") { _, _ ->
                            val intent = Intent(this@MapActivity, CourseFileSaveActivity::class.java)
                            startActivity(intent)
                        }
                        .setNegativeButton("아니오"){_,_ -> }
                        .setCancelable(false)
                        .create()
                        .show()
                }
                else{
                    AlertDialog.Builder(this@MapActivity)
                        .setTitle("네트워크 연결 실패")
                        .setMessage("\n네트워크에 문제가 발생하였습니다.\n인터넷 연결 상태를 확인하고\n확인 버튼을 다시 눌러주세요")
                        .setPositiveButton("재시도") { _, _ -> postMyRecord(myRecordData)}
                        .setNegativeButton("취소하기") { _, _ ->

                            AlertDialog.Builder(this@MapActivity)
                                .setTitle("취소 확인")
                                .setMessage("\n정말로 취소하시겠습니까?")
                                .setPositiveButton("확인") { _, _ -> }
                                .setNegativeButton("취소"){ _, _ -> postMyRecord(myRecordData)}
                                .setCancelable(false)
                                .create()
                                .show()

                        }
                        .setCancelable(false)
                        .create()
                        .show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                AlertDialog.Builder(this@MapActivity)
                    .setTitle("네트워크 연결 실패")
                    .setMessage("\n네트워크에 문제가 발생하였습니다.\n인터넷 연결 상태를 확인하고\n확인 버튼을 다시 눌러주세요")
                    .setPositiveButton("재시도") { _, _ -> postMyRecord(myRecordData)}
                    .setNegativeButton("취소하기") { _, _ ->

                        AlertDialog.Builder(this@MapActivity)
                            .setTitle("취소 확인")
                            .setMessage("\n정말로 취소하시겠습니까?")
                            .setPositiveButton("확인") { _, _ -> }
                            .setNegativeButton("취소"){ _, _ -> postMyRecord(myRecordData)}
                            .setCancelable(false)
                            .create()
                            .show()

                    }
                    .setCancelable(false)
                    .create()
                    .show()
            }
        })

    }

    private fun toggleFab() {

        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션 세팅
        if (isFabOpen) {
            ObjectAnimator.ofFloat(binding.fabBikeRoad, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabBikeCenter, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabUnofficialInformation, "translationY", 0f).apply { start() }
            binding.fabMain.setImageResource(R.drawable.ic_baseline_add_24)

            // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션 세팅
        } else {
            ObjectAnimator.ofFloat(binding.fabBikeRoad, "translationY", 200f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabBikeCenter, "translationY", 400f).apply { start() }
            ObjectAnimator.ofFloat(binding.fabUnofficialInformation, "translationY", 600f).apply { start() }
            binding.fabMain.setImageResource(R.drawable.ic_baseline_clear_24)
        }

        isFabOpen = !isFabOpen

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    //메세지 수신 콜백
    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val dis = intent.getIntExtra("dis", 0)
            val spd = intent.getFloatExtra("spd",0.0f)
            val kcal = intent.getDoubleExtra("kcal",0.0)
            val pause = intent.getBooleanExtra("pause",false)

            if(pause && running){
                running = false
                timeWhenStopped = binding.chronometer.base - SystemClock.elapsedRealtime();
                binding.chronometer.stop();

                Toast.makeText(applicationContext, "일시 정지 상태입니다.", Toast.LENGTH_SHORT).show()

            }
            else if(!running && !pause){
                running = true
                binding.chronometer.base = SystemClock.elapsedRealtime() + timeWhenStopped
                binding.chronometer.start()
            }

            if(!pause){
                distValue = dis
                calValue = ((kcal * 10).toInt() / 10.0).toInt()

                if(dis>=1000) binding.tvMapDistance.text = ((dis/100)/10.0).toString()+"km"
                else binding.tvMapDistance.text = dis.toString() +"m"

                binding.tvMapSpeed.text = (((spd*3.6*100).toInt())/100.0).toString()+"km/h"
                binding.tvMapKCal.text = ((kcal * 10).toInt() / 10.0).toString() + "kcal"
            }

        }
    }
    private fun start(){
        running = true
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mMessageReceiver, IntentFilter("location"))
        var intent = Intent(this, TestService::class.java)
        startService(intent)
    }

    private fun end(){
        timeWhenStopped = 0
        binding.chronometer.stop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        var intent = Intent(this, TestService::class.java)
        stopService(intent)
    }

    override fun onBackPressed() {

        if(running){
            AlertDialog.Builder(this@MapActivity)
                .setTitle("기록모드 종료 확인")
                .setMessage("\n현재 기록이 진행 중 입니다!\n정말로 기록 모드를 종료하시겠습니까?")
                .setPositiveButton("확인") { _, _ -> super.onBackPressed() }
                .setNegativeButton("취소"){ _, _ -> }
                .setCancelable(false)
                .create()
                .show()
        }
        else super.onBackPressed()

    }

//    override fun finish() {
//
//        if(running){
//            AlertDialog.Builder(this@MapActivity)
//                .setTitle("취소 확인")
//                .setMessage("\n정말로 취소하시겠습니까?")
//                .setPositiveButton("확인") { _, _ -> super.finish() }
//                .setNegativeButton("취소"){ _, _ -> }
//                .setCancelable(false)
//                .create()
//                .show()
//        }
//        else super.finish()
//
//
//    }


    // GPX 파서 라이브러리로 gpx 파싱하는 코드
    //        val path = PathOverlay()
    //        val parser = GPXParser() // consider injection
//        try {
//
//            val input: InputStream = assets.open("abc.gpx")
//            val parsedGpx: Gpx? = parser.parse(input) // consider using a background thread
//            parsedGpx?.let {
//
////                Log.d("HAHA", it.tracks[0].trackSegments[0].trackPoints.size.toString())
////                Log.d("HAHA", it.tracks[0].trackSegments[0].trackPoints[0].longitude.toString())
////                val bb = LatLng(it.tracks[0].trackSegments[0].trackPoints[0].latitude, it.tracks[0].trackSegments[0].trackPoints[0].longitude)
//                var mutableList = mutableListOf<LatLng>()
//
//
//                for(i in 0..it.tracks[0].trackSegments[0].trackPoints.size-1){
//                    mutableList.add(LatLng(it.tracks[0].trackSegments[0].trackPoints[i].latitude, it.tracks[0].trackSegments[0].trackPoints[i].longitude))
//                    Log.d("HAHAHA", it.tracks[0].trackSegments[0].trackPoints[i].latitude.toString() + " " +  it.tracks[0].trackSegments[0].trackPoints[i].longitude.toString())
//                }
//
//
//                path.coords = mutableList
//                path.patternImage = OverlayImage.fromResource(R.drawable.path_pattern)
//                path.patternInterval = 60
//                path.outlineWidth = 0
//                path.width = 25
//                path.color = Color.BLUE
//                path.map = naverMap
//                naverMap.cameraPosition = CameraPosition(LatLng(it.tracks[0].trackSegments[0].trackPoints[0].latitude, it.tracks[0].trackSegments[0].trackPoints[0].longitude), 16.0)
//            } ?: {
//                // error parsing track
//            }
//        } catch (e: IOException) {
//            // do something with this exception
//            e.printStackTrace()
//        } catch (e: XmlPullParserException) {
//            // do something with this exception
//            e.printStackTrace()
//        }


}