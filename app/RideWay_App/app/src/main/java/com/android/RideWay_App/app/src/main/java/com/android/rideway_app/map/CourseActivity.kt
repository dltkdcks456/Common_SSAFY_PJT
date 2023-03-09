package com.android.rideway_app.map

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.android.rideway_app.databinding.ActivityCourseBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.fileUpload.CourseListDataResponse
import com.android.rideway_app.retrofit.fileUpload.FileService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CourseActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCourseBinding

    private var keyword = ""
    private var searched = false
    private var page = 0

    private val list: MutableList<CourseListDataResponse.Content> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.boardRecyclerView.adapter = CourseListAdapter(list)
        binding.boardRecyclerView.layoutManager = LinearLayoutManager(this@CourseActivity)

        // 스크롤 끝 감지
        binding.boardRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!binding.boardRecyclerView.canScrollVertically(1)) {
                    ++page
                    if(!searched)
                        getList()
                    else getListWithSearch()
                }
            }
        })

        binding.btnSearch.setOnClickListener {
            this.keyword = binding.etSearchKeyword.text.toString()
            this.page = 0
            this.searched = true
            getListWithSearch()
        }

        getList()
    }

    override fun onResume() {
        super.onResume()
        getList()
    }

    private fun getList(){
        CoroutineScope(Dispatchers.IO).launch {

            if(page == 0){
                list.clear()
            }
            list.addAll(RetrofitClient.getInstance().create(FileService::class.java).getCourseList(page).content)

            launch(Dispatchers.Main){
                binding.boardRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }


    private fun getListWithSearch(){
        CoroutineScope(Dispatchers.IO).launch {

            if(page == 0){
                list.clear()
            }
            list.addAll(RetrofitClient.getInstance().create(FileService::class.java).getCourseListWithSearch(page,keyword).content)

            launch(Dispatchers.Main){
                binding.boardRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }
}