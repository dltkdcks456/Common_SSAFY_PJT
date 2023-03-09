package com.android.rideway_app.deal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.community.BoardListAdapter
import com.android.rideway_app.databinding.ActivityDealBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.community.BoardDataResponse
import com.android.rideway_app.retrofit.deal.DealListResponse
import com.android.rideway_app.retrofit.deal.DealService
import com.naver.maps.map.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DealActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDealBinding

    private val retrofitAPI = RetrofitClient.getInstance().create(DealService::class.java)

    private var dealList = mutableListOf<DealListResponse.Content>()

    private var searchingOption = "title"

    private var keyword : String = ""

    private var searched : Boolean = false

    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val options = arrayOf("제목","작성자")
        val adapter = ArrayAdapter(binding.root.context,
            R.layout.support_simple_spinner_dropdown_item,options)

        binding.spSearchOptions.adapter = adapter

        //스피너 선택 이벤트 부여
        binding.spSearchOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                searchingOption = when(pos) {
                    1 -> "user_id"
                    else -> "title"
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                searchingOption = "title"
            }
        }

        // 스크롤 끝 감지
        binding.dealRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!binding.dealRecyclerView.canScrollVertically(1)) {
                    if(!searched)
                        getDealList(++page)
                    else getDealListWithSearch(++page)
                }
            }
        })

        binding.btnSearch.setOnClickListener {
            this.keyword = binding.etSearchKeyword.text.toString()
            this.page = 0
            this.searched = true
            getDealListWithSearch(this.page)
        }

        binding.btnInsert.setOnClickListener {
            val intent = Intent(this@DealActivity,DealWriteActivity::class.java)
            startActivity(intent)
        }

        getDealList(this.page)
    }

    override fun onResume() {
        super.onResume()
        this.page = 0
        if(!searched)
            getDealList(this.page)
        else getDealListWithSearch(this.page)
    }

    private fun getDealList(dealPage : Int){
        retrofitAPI.getDealList(dealPage).enqueue(object : Callback<DealListResponse>{
            override fun onResponse(
                call: Call<DealListResponse>,
                response: Response<DealListResponse>
            ) {
                if(response.isSuccessful){
                    if(dealPage == 0){
                        dealList = response.body()!!.content
                        setBoard()
                    }else{
                        dealList.addAll(response.body()!!.content)
                        binding.dealRecyclerView.adapter!!.notifyDataSetChanged()
                    }
                }else{
                    Toast.makeText(this@DealActivity,"리스트를 불러오지 못했습니다.",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DealListResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@DealActivity,"네트워크 오류",Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun getDealListWithSearch(dealPage : Int){
        retrofitAPI.getDealListWithSearch(dealPage,keyword).enqueue(object : Callback<DealListResponse>{
            override fun onResponse(
                call: Call<DealListResponse>,
                response: Response<DealListResponse>
            ) {
                if(response.isSuccessful){
                    if(dealPage == 0){
                        dealList = response.body()!!.content
                        setBoard()
                    }else{
                        dealList.addAll(response.body()!!.content)
                        binding.dealRecyclerView.adapter!!.notifyDataSetChanged()
                    }
                }else{
                    Toast.makeText(this@DealActivity,"리스트를 불러오지 못했습니다.",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DealListResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@DealActivity,"네트워크 오류",Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun setBoard(){
        val adapter = DealListAdapter(dealList)
        binding.dealRecyclerView.adapter = adapter
        binding.dealRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}

