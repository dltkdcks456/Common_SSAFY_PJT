package com.android.rideway_app.service

import android.Manifest
import android.app.Activity
import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.*
import io.jenetics.jpx.GPX
import io.jenetics.jpx.Track
import io.jenetics.jpx.TrackSegment
import io.jenetics.jpx.WayPoint
import java.io.*
import java.util.*
import kotlin.io.path.Path
import kotlin.math.abs
import kotlin.math.atan


class TestService : Service() {
    private val timer: Timer = Timer(true)
    private var serializerFile: File? = null
    private var oos: ObjectOutputStream? = null
    private var basetime: Long? = null
    private var location : Location ? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null

    private lateinit var userProfile: SharedPreferences

    // 전에 있었던 gps 좌표 값 저장용
    private var before_latitude = 0.0
    private var before_longitude = 0.0
    private var before_altitude = 0.0

    // total 값
    private var total_weight = 77
    private var total_distance = 0
    private var total_kcal = 0.0

    // 이때까지 0m/s가 나온 횟수를 저장한다.
    private var zero_count = false
    private var pause = false

    // 위치 정보를 요청하는 변수
    internal var mLocationRequest: LocationRequest =  LocationRequest.create().apply {
        interval = 1000 // 업데이트 간격 단위(밀리초)
        fastestInterval = 0 // 가장 빠른 업데이트 간격 단위(밀리초)
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY // 정확성
        maxWaitTime= 0 // 위치 갱신 요청 최대 대기 시간 (밀리초)
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("Service","경로 기록 시작")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//            startForeground(
//            notificationId,
//            builder!!.build())
//        else
//            notificationManager!!.notify(notificationId, builder!!.build())
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)
        total_weight = userProfile.getInt("cycle_weight", 12) + userProfile.getInt("weight",65)

        // 파일 이름 정해주는 부분인듯
        val name = "gpxFile"

        // 파일 경로 정의 및 없으면 경로 만들어줌
        val path = File(filesDir.absolutePath + "/gpx")
        while (!path.exists()) path.mkdirs()
        try {
            serializerFile = File(filesDir.absolutePath + "/gpx/" + name)
            if (serializerFile!!.exists()) serializerFile!!.delete()
            while (!serializerFile!!.exists()) serializerFile!!.createNewFile()
            oos = ObjectOutputStream(FileOutputStream(serializerFile))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        // ?
        basetime = 0L
        Toast.makeText(this, "경로 기록 시작", Toast.LENGTH_SHORT).show()


        //FusedLocationProviderClient의 인스턴스를 생성. ----> 앞에서 하기 때문에 안해도 되지만 일단 놔둠
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { }

        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청합니다.
        mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
        startTrack()
        // 서비스가 동작하는거에 대한 옵션
        return START_STICKY
    }
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    // 시스템으로 부터 받은 위치정보를 갱신해주는 메소드
    fun onLocationChanged(location: Location) {
        this.location = location
    }

    //액티비티로 메세지 전달
    private fun sendMessage(distance : Int, speed : Float, kcal : Double) {
        val intent = Intent("location")
        intent.putExtra("dis", distance)
        intent.putExtra("kcal", kcal)
        intent.putExtra("spd", speed)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun sendPauseMessage() {
        val intent = Intent("location")
        intent.putExtra("pause", true)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

// 맵에서 stopService를 수행하면 이 부분이 실행됨
    override fun onDestroy() {
        try {
            // 파일 쓰기를 멈추고 저장함
            endTrack()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        super.onDestroy()
    }

    //경로 기록 종료
    @Throws(IOException::class)
    private fun endTrack() {
        timer.cancel()
        val ois = ObjectInputStream(FileInputStream(serializerFile))
        val segment = TrackSegment.builder()
        while (true) {
            try {
                val wp = ois.readObject() as WayPoint
                segment.addPoint(wp)
            } catch (e: EOFException) {
                break
            } catch (ignored: ClassNotFoundException) {
            }
        }
        
        val gpx = GPX.builder().addTrack { track: Track.Builder ->
            track.addSegment(
                segment.build()
            )
        }.build()
        //gpx 파일 생성
        GPX.write(gpx, Path(serializerFile!!.absolutePath + ".gpx"))
        Toast.makeText(this, "기록 종료", Toast.LENGTH_SHORT).show()
    }

    private fun startTrack() {
        val task: TimerTask = object : TimerTask() {
            override fun run() {
                val location = getLocation()
                if (location != null) {
                    try {
                        // 1초마다 계산하는 메소드
                        if (location.time != basetime) {
                            val speed = location.speed

                            Log.d("speed", speed.toString())

                            // 속도가 평균 걷는 속도 보다 느리면 일시정지 플래그를 켠다.
                            zero_count = speed * 3.6 < 2.5

                            // 만약 5초 이상 카운트 됐다면 일시정지 신호를 보내준다.
                            if(zero_count){
                                sendPauseMessage()
                            }
                            else{
                                // 0 카운트를 다시 0으로 만들어준다.

                                // 지금이 처음 기록이 시작되었는지 확인해본다. 처음 시작되었다면 before 값만 세팅 해준다.
                                if(before_latitude == 0.0 && before_longitude == 0.0){
                                    before_latitude = location.latitude
                                    before_longitude = location.longitude
                                    before_altitude = location.altitude
                                }
                                else{
                                    // 거리를 구하기 위해 Location을 만들어준다.
                                    val startPoint = Location("locationStart")
                                    val endPoint = Location("locationEnd")

                                    startPoint.latitude = before_latitude
                                    startPoint.longitude = before_longitude

                                    endPoint.latitude = location.latitude
                                    endPoint.longitude = location.longitude

                                    before_latitude = location.latitude
                                    before_longitude = location.longitude

                                    var distance = startPoint.distanceTo(endPoint).toInt()

                                    if(distance > speed+1) distance = speed.toInt()
                                    var cal_val = 0.0

                                    when(speed.toInt()){
                                        in 0..3  -> cal_val = 0.045
                                        4 -> cal_val = 0.065
                                        5 -> cal_val = 0.078
                                        6 -> cal_val = 0.098
                                        7 -> cal_val = 0.135
                                        8 -> cal_val = 0.172
                                        9 -> cal_val = 0.198
                                        10 -> cal_val = 0.232
                                        11 -> cal_val = 0.308
                                        in 12..50 -> 0.4
                                    }

                                    // 총 무게(자전거 + 몸) * 속도에 따른 칼로리 지수 * 60초 / 60 = 1초당 칼로리
                                    var kcal = total_weight * cal_val / 60.0


                                    // 각도를 구한다.
                                    val degree = abs(atan(location.altitude - before_altitude / distance)*57.2958)

                                    // 내리막인지 확인한다.
                                    if(before_altitude > location.altitude){

                                        // 내리막일 때 각도에 따라 값을 달리 한다.
                                        when(degree){
                                            in 0.0..2.2 -> total_kcal += kcal
                                            in 2.3..3.4 -> total_kcal += kcal/2.0
                                            in 3.5..5.1 -> total_kcal += kcal/10.0
                                        }

                                    }
                                    else{

                                        // 오르막일 때 각도에 따라 칼로리 값을 더 더해준다.
                                        when(degree){
                                            in 0.0..2.2 -> total_kcal += kcal
                                            in 2.3..3.4 -> total_kcal += kcal + kcal/10.0
                                            in 3.5..5.6 -> total_kcal += kcal + kcal/5.0
                                            in 5.2..8.4 -> total_kcal += kcal + kcal/3.0
                                        }
                                        if(degree>8.5){
                                            total_kcal += kcal + kcal/2.0
                                        }

                                    }

                                    // 토탈 거리에 현재 거리를 더해준다.
                                    total_distance += distance

                                    sendMessage(total_distance, speed, total_kcal)

                                    //액티비티로 메세지 발신
                                    basetime = location.time

                                    val point =
                                        WayPoint.builder().lat(location.latitude).lon(location.longitude)
                                            .ele(location.altitude).time(location.time).build()

                                    // 바로 위 포인트 값이 null이 아니면 gpx파일에 기록한다.
                                    if (point != null) {
                                        oos!!.writeObject(point)
//                                        Log.i(
//                                            "track",
//                                            location.latitude.toString() + "," + location.longitude + "," + location.altitude + ", speed : " + location.speedAccuracyMetersPerSecond
//                                        )
                                    }
                                }
                            }




                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        timer.schedule(task, 0,1000)
    }
    private fun getLocation() : Location? {
        return this.location
    }

}