package com.android.rideway_app.community

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.rideway_app.MainActivity
import com.android.rideway_app.R
import com.android.rideway_app.databinding.ActivityBoardCommentBinding
import com.android.rideway_app.mainapp.MainApplication
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.community.BoardDetailResponse
import com.android.rideway_app.retrofit.community.BoardService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


class BoardCommentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBoardCommentBinding
    private lateinit var commentList : List<BoardDetailResponse.Comment>
    private val retrofitAPI = RetrofitClient.getInstance().create(BoardService::class.java)

    private var board_id = 0
    private var board_code = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        board_id = intent.getIntExtra("boardId",0)
        board_code = intent.getIntExtra("boardCode",0)
        binding.btnCommentInsert.setOnClickListener {
            val data = hashMapOf<String,String>()
            data.put("user_id",MainApplication.getUserId().toString())
            data.put("board_id",board_id.toString())
            data.put("content",binding.etComment.text.toString())
            CoroutineScope(Dispatchers.IO).launch {
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
        retrofitAPI.getBoardDetail(MainApplication.getUserId(),board_code,board_id).enqueue(object : Callback<BoardDetailResponse>{
            override fun onResponse(
                call: Call<BoardDetailResponse>,
                response: Response<BoardDetailResponse>
            ) {
                if(response.isSuccessful){
                    val adapter = CommentListAdapter(response.body()!!.comment,object : BoardCommentChannel{
                        override fun setList() {
                            getList()
                        }
                    })
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.layoutManager = LinearLayoutManager(this@BoardCommentActivity)
                }
            }

            override fun onFailure(call: Call<BoardDetailResponse>, t: Throwable) {

            }

        })

    }
}