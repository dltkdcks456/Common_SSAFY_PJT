package com.android.rideway_app.meeting

import android.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.rideway_app.databinding.ActivityCommunityUpdateBinding
import com.android.rideway_app.databinding.ActivityCommunityWriteBinding
import com.android.rideway_app.mainapp.MainApplication
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.meeting.CommunityDetailResponse
import com.android.rideway_app.retrofit.meeting.CommunityInsertData
import com.android.rideway_app.retrofit.meeting.CommunityService
import com.android.rideway_app.retrofit.region.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.time.LocalDateTime

class CommunityUpdateActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunityUpdateBinding

    private val retrofitAPI = RetrofitClient.getInstance().create(CommunityService::class.java)

    private lateinit var siList : SiData
    private lateinit var gunList : GunData
    private lateinit var dongList : DongData

    private var si : String = ""
    private var gun : String = ""
    private var dong : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInsert.setOnClickListener {
            communityUpdate()
        }

        //기존 정보 세팅
        setDetail()

        binding.date.setOnClickListener {
            val dateListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val reMonth = if(10 > month) "0${month+1}" else "${month+1}"
                val reDay = if(10 > dayOfMonth) "0${dayOfMonth}" else "$dayOfMonth"
                binding.date.setText("${year}-${reMonth}-${reDay}")
            }
            DatePickerDialog(this, com.android.rideway_app.R.style.MySpinnerDatePickerStyle ,dateListener,2013,1,1).show()
        }

        binding.time.setOnClickListener {
            val timeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val reHour = if(10 > hourOfDay) "0$hourOfDay" else "$hourOfDay"
                val reMinutes = if(10 > minute) "0$minute" else "$minute"
                binding.time.setText("${reHour}:${reMinutes}:00")
            }
            TimePickerDialog(this, com.android.rideway_app.R.style.MySpinnerDatePickerStyle ,timeListener,0,0,true).show()
        }

        getSi()
    }


    private fun communityUpdate(){
        val title = binding.title.text.toString()
        val content = binding.content.text.toString()
        val startTime = "${binding.date.text} ${binding.time.text}"
        val max = binding.max.text.toString()

        retrofitAPI.updateCommunity(
            CommunityInsertData(
                MainApplication.getUserToken(),
                title,
                content,
                si,
                gun,
                dong,
                max,
                startTime,
                true,
                intent.getStringExtra("communityId").toString()
            )
        ).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    Toast.makeText(this@CommunityUpdateActivity, "등록 성공", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@CommunityUpdateActivity,"등록 실패",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CommunityUpdateActivity,"네트워크 오류",Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun getSi(){
        val retrofitAPI = RetrofitClient.getInstance().create(RegionService::class.java)

        retrofitAPI.getAllSi().enqueue(object : Callback<SiData> {
            override fun onResponse(call: Call<SiData>, response: Response<SiData>) {
                if(response.isSuccessful){
                    siList = response.body()!!
                    siList.add(0 , SiDataItem("전체",0))
                    val list = mutableListOf<String>()
                    for(i in siList) list.add(i.name)
                    binding.spSi.adapter = ArrayAdapter<String>(this@CommunityUpdateActivity, R.layout.simple_list_item_1, list)
                    binding.spSi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            if(siList[position].si_code == 0){
                                si = ""
                                gun = ""
                                dong = ""
                                binding.spGun.adapter = null
                                binding.spDong.adapter = null
                            }else{
                                getGun(siList[position].si_code.toString())
                                si = siList[position].name
                                gun = ""
                                dong = ""
                            }
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
                }else{
                    Toast.makeText(this@CommunityUpdateActivity,"시 목록을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<SiData>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@CommunityUpdateActivity,"네트워크 오류", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getGun(si_code : String){
        val retrofitAPI = RetrofitClient.getInstance().create(RegionService::class.java)

        retrofitAPI.getSelectedGun(si_code).enqueue(object : Callback<GunData> {
            override fun onResponse(call: Call<GunData>, response: Response<GunData>) {
                if(response.isSuccessful){
                    gunList = response.body()!!
                    gunList.add(0, GunDataItem(0,"전체",0))
                    val list = mutableListOf<String>()
                    for(i in gunList) list.add(i.name)
                    binding.spGun.adapter = ArrayAdapter<String>(this@CommunityUpdateActivity, R.layout.simple_list_item_1, list)
                    binding.spGun.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            if(gunList[position].gun_code == 0) {
                                gun = ""
                                dong = ""

                                binding.spDong.adapter = null
                            }else{
                                getDong(gunList[position].si_code.toString(),gunList[position].gun_code.toString())
                                gun = gunList[position].name
                                dong = ""
                            }
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
                }else{
                    Toast.makeText(this@CommunityUpdateActivity,"군 목록을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<GunData>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@CommunityUpdateActivity,"네트워크 오류", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getDong(si_code : String,gun_code : String){
        val retrofitAPI = RetrofitClient.getInstance().create(RegionService::class.java)

        retrofitAPI.getSelectedDong(si_code,gun_code).enqueue(object : Callback<DongData> {
            override fun onResponse(call: Call<DongData>, response: Response<DongData>) {
                if(response.isSuccessful){
                    dongList = response.body()!!
                    dongList.add(0, DongDataItem(0,0,"전체",0))
                    val list = mutableListOf<String>()
                    for(i in dongList) list.add(i.name)
                    binding.spDong.adapter = ArrayAdapter<String>(this@CommunityUpdateActivity, R.layout.simple_list_item_1, list)
                    binding.spDong.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            if(dongList[position].dong_code == 0){
                                dong = ""
                            }else{
                                dong = dongList[position].name
                            }
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
                }else{
                    Toast.makeText(this@CommunityUpdateActivity,"군 목록을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<DongData>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@CommunityUpdateActivity,"네트워크 오류", Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun setDetail(){
        val communityId = intent.getStringExtra("communityId")!!
        println(communityId)
        retrofitAPI.getDetail(communityId).enqueue(object : Callback<CommunityDetailResponse>{
            override fun onResponse(
                call: Call<CommunityDetailResponse>,
                response: Response<CommunityDetailResponse>,
            ) {
                if(response.isSuccessful){
                    val content = response.body()!!
                    val timeInfo = content.date.split("T")
                    binding.title.setText(content.title)
                    binding.date.setText(timeInfo[0])
                    binding.time.setText(timeInfo[1])
                    si = content.si
                    gun = content.gun
                    dong = content.dong
                    binding.content.setText(content.content)
                    binding.max.setText(content.max_person.toString())
                }
            }

            override fun onFailure(call: Call<CommunityDetailResponse>, t: Throwable) {

            }

        })
    }



}