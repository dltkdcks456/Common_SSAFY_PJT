package com.android.rideway_app

import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.rideway_app.databinding.ActivityTestBinding
import com.android.rideway_app.service.TestService


class TestActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTestBinding


    private val PERMISSONS_REQUEST_CODE = 100



    var REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    lateinit var getGPSPermissionLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnStart.setOnClickListener { start() }
        binding.btnEnd.setOnClickListener { end() }

        checkAllPermissions()

    }

    private fun checkAllPermissions(){
        if(!isLocationServiceAvailable()){
            showDialogForLocationServiceSetting()
        }else{
            isRuntimePermissionsGranted()
        }
    }

    fun isLocationServiceAvailable() : Boolean{
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    fun isRuntimePermissionsGranted() {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(this@TestActivity,android.Manifest.permission.ACCESS_FINE_LOCATION)

        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this@TestActivity,android.Manifest.permission.ACCESS_COARSE_LOCATION)

        if(hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
            hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@TestActivity,REQUIRED_PERMISSIONS,PERMISSONS_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSONS_REQUEST_CODE && grantResults.size == REQUIRED_PERMISSIONS.size){
            var checkResult = true

            for(result in grantResults){
                if(result != PackageManager.PERMISSION_GRANTED){
                    checkResult = false
                    break
                }
            }
            if(checkResult){

            }else{
                Toast.makeText(this@TestActivity,"퍼미션이 거부되었습니다. ",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun showDialogForLocationServiceSetting(){
        getGPSPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(isLocationServiceAvailable()){
                    isRuntimePermissionsGranted()
                }else{
                    Toast.makeText(this@TestActivity,"퍼미션이 거부되었습니다. ",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        val builder : AlertDialog.Builder = AlertDialog.Builder(this@TestActivity)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage("위치서비스 활성 요망")
        builder.setCancelable(true)
        builder.setPositiveButton("설정",
        DialogInterface.OnClickListener { dialog, id ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            getGPSPermissionLauncher.launch(callGPSSettingIntent)
        })

        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
                Toast.makeText(this@TestActivity,"퍼미션이 거부되었습니다. ",Toast.LENGTH_SHORT).show()
                finish()
            })
        builder.create().show()
    }
    
    //메세지 수신 콜백
    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val lat = intent.getStringExtra("lat")
            val lng = intent.getStringExtra("lng")
            val alt = intent.getStringExtra("alt")
            //전달받은 메세지를 이용해 실행시킬 메소드
            setLocation(lat,lng,alt,"0")
        }
    }
    private fun start(){
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mMessageReceiver, IntentFilter("location"))
        var intent = Intent(this,TestService::class.java)
        startService(intent)
    }

    private fun end(){
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        var intent = Intent(this,TestService::class.java)
        stopService(intent)
    }

    fun setLocation(lat : String? , lng : String? , ele : String? , time : String?){
        binding.lat.text = lat
        binding.lng.text = lng
        binding.ele.text = ele
        binding.time.text = time
    }
}