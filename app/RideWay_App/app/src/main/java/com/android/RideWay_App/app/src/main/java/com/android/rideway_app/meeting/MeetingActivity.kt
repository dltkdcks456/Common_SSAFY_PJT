package com.android.rideway_app.meeting

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.databinding.ActivityMeetingBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.meeting.CommunityListResponse
import com.android.rideway_app.retrofit.meeting.CommunityService
import com.android.rideway_app.retrofit.region.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MeetingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMeetingBinding

    private lateinit var siList : SiData
    private lateinit var gunList : GunData
    private lateinit var dongList : DongData

    //검색 조건
    private var si : String = ""
    private var gun : String = ""
    private var dong : String = ""
    private var keyword : String = ""

    //현재 검색한 상태인지 확인
    private var searched : Boolean = false

    private var page = 0

    //모임 리스트
    private var list : MutableList<CommunityListResponse.Content> = mutableListOf()
    private val adapter = CommunityListAdapter(list,object : CommunityChannel{
        override fun setList() {
            if(!searched){
                getList()
            }else{
                getListWithSearch()
            }
        }
    })

    private val retrofitAPI = RetrofitClient.getInstance().create(CommunityService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeetingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            keyword = binding.etSearchKeyword.text.toString()
            searched = true
            page = 0
            getListWithSearch()
        }

        binding.btnGoWrite.setOnClickListener {
            val intent = Intent(this,CommunityWriteActivity::class.java)
            startActivity(intent)
        }
        
        //리스트 끝감지
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!binding.recyclerView.canScrollVertically(1)) {
                    ++page
                    if(searched){
                        getListWithSearch()
                    }else{
                        getList()
                    }
                }
            }
        })

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        getSi()

        getList()
    }
    override fun onResume(){
        super.onResume()
        getList()
    }

    private fun getSi(){
        val retrofitAPI = RetrofitClient.getInstance().create(RegionService::class.java)

        retrofitAPI.getAllSi().enqueue(object : Callback<SiData>{
            override fun onResponse(call: Call<SiData>, response: Response<SiData>) {
                if(response.isSuccessful){
                    siList = response.body()!!
                    siList.add(0 , SiDataItem("전체",0))
                    val list = mutableListOf<String>()
                    for(i in siList) list.add(i.name)
                    binding.spSi.adapter = ArrayAdapter<String>(this@MeetingActivity, R.layout.simple_list_item_1, list)
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
                    Toast.makeText(this@MeetingActivity,"시 목록을 가져오지 못했습니다.",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<SiData>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@MeetingActivity,"네트워크 오류",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getGun(si_code : String){
        val retrofitAPI = RetrofitClient.getInstance().create(RegionService::class.java)

        retrofitAPI.getSelectedGun(si_code).enqueue(object : Callback<GunData>{
            override fun onResponse(call: Call<GunData>, response: Response<GunData>) {
                if(response.isSuccessful){
                    gunList = response.body()!!
                    gunList.add(0,GunDataItem(0,"전체",0))
                    val list = mutableListOf<String>()
                    for(i in gunList) list.add(i.name)
                    binding.spGun.adapter = ArrayAdapter<String>(this@MeetingActivity, R.layout.simple_list_item_1, list)
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
                    Toast.makeText(this@MeetingActivity,"군 목록을 가져오지 못했습니다.",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<GunData>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@MeetingActivity,"네트워크 오류",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getDong(si_code : String,gun_code : String){
        val retrofitAPI = RetrofitClient.getInstance().create(RegionService::class.java)

        retrofitAPI.getSelectedDong(si_code,gun_code).enqueue(object : Callback<DongData>{
            override fun onResponse(call: Call<DongData>, response: Response<DongData>) {
                if(response.isSuccessful){
                    dongList = response.body()!!
                    dongList.add(0,DongDataItem(0,0,"전체",0))
                    val list = mutableListOf<String>()
                    for(i in dongList) list.add(i.name)
                    binding.spDong.adapter = ArrayAdapter<String>(this@MeetingActivity, R.layout.simple_list_item_1, list)
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
                    Toast.makeText(this@MeetingActivity,"군 목록을 가져오지 못했습니다.",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<DongData>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@MeetingActivity,"네트워크 오류",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getList(){
        retrofitAPI.getList(page).enqueue(object : Callback<CommunityListResponse>{
            override fun onResponse(
                call: Call<CommunityListResponse>,
                response: Response<CommunityListResponse>
            ) {
                if(response.isSuccessful){
                    println(response.body()!!.content)
                    if(page == 0) list.clear()
                    list.addAll(response.body()!!.content)
                    binding.recyclerView.adapter!!.notifyDataSetChanged()

                }else{
                    Toast.makeText(this@MeetingActivity,"목록을 불러올 수 없습니다.",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CommunityListResponse>, t: Throwable) {
                Toast.makeText(this@MeetingActivity,"네트워크 오류",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getListWithSearch(){
        retrofitAPI.getListWithSearch(page,si,gun,dong,keyword).enqueue(object : Callback<CommunityListResponse>{
            override fun onResponse(
                call: Call<CommunityListResponse>,
                response: Response<CommunityListResponse>
            ) {
                if(response.isSuccessful){
                    println(response.body()!!.content)
                    if(page == 0) list.clear()
                    list.addAll(response.body()!!.content)
                    binding.recyclerView.adapter!!.notifyDataSetChanged()

                }else{
                    Toast.makeText(this@MeetingActivity,"목록을 불러올 수 없습니다.",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CommunityListResponse>, t: Throwable) {
                Toast.makeText(this@MeetingActivity,"네트워크 오류",Toast.LENGTH_SHORT).show()
            }

        })
    }
}