package com.android.rideway_app.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.rideway_app.R
import com.android.rideway_app.databinding.ActivityBoardCommentBinding
import com.android.rideway_app.mainapp.MainApplication
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.community.BoardDetailResponse
import com.android.rideway_app.retrofit.fileUpload.FileService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class CourseCommentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBoardCommentBinding
    private val retrofitAPI = RetrofitClient.getInstance().create(FileService::class.java)

    private var courseBoardId : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        courseBoardId = intent.getLongExtra("courseBoardId",0)

        binding.btnCommentInsert.setOnClickListener {
            val data = hashMapOf<String,String>()
            data.put("user_id",MainApplication.getUserId().toString())
            data.put("course_board_id",courseBoardId.toString())
            data.put("content",binding.etComment.text.toString())
            CoroutineScope(Dispatchers.IO).launch {
                println(data)
                retrofitAPI.insertComment(data)
                getList()
            }
            binding.etComment.setText("")
        }
        getList()
    }

    override fun finish(){
        super.finish()
        overridePendingTransition(0, R.anim.slide_down_exit)
    }

    private fun getList(){
        CoroutineScope(Dispatchers.IO).launch {
            println(courseBoardId)
            val content = async {
                retrofitAPI.getCourseDetail(courseBoardId,MainApplication.getUserId().toLong())
            }

            launch(Dispatchers.Main) {
                val adapter = CommentListAdapter(content.await().comment,object : CourseCommentChannel{
                    override fun setList() {
                        getList()
                    }
                })
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(this@CourseCommentActivity)
            }
        }
    }
}