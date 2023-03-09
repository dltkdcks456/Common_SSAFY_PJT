package com.android.rideway_app.community

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.rideway_app.MainActivity
import com.android.rideway_app.R
import com.android.rideway_app.databinding.ActivityBoardDetailBinding
import com.android.rideway_app.mainapp.MainApplication
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.community.BoardDetailResponse
import com.android.rideway_app.retrofit.community.BoardService
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import org.sufficientlysecure.htmltextview.HtmlTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BoardDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardDetailBinding
    private lateinit var userInfo: SharedPreferences

    private lateinit var boardTitle : String
    private lateinit var boardContent : String

    private lateinit var commentList : List<BoardDetailResponse.Comment>

    private var board_code = 0
    private var board_num = 0


    companion object{
        var mContext : Context? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mContext = this
        userInfo = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        board_code = intent.getIntExtra("boardCode",0)
        board_num = intent.getIntExtra("boardNum",0)
        val board_name = intent.getStringExtra("boardName")
        binding.toolbarTitle.text = board_name
        binding.board.text = board_name
        setBoardDetail(board_code,board_num)

        binding.goComment.setOnClickListener{
            val intent = Intent(this,BoardCommentActivity::class.java)
            intent.putExtra("boardId",board_num)
            intent.putExtra("boardCode",board_code)
            startActivity(intent)
//            overridePendingTransition(R.anim.slide_up_enter,0)
        }
        binding.btnUpdate.setOnClickListener {
            val intent = Intent(this,BoardUpdateActivity::class.java)
            intent.putExtra("code",board_code)
            intent.putExtra("boardNum",board_num)
            intent.putExtra("title",boardTitle)
            intent.putExtra("content",boardContent)
            startActivity(intent)
        }
        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("게시글 삭제")
                .setMessage("게시글을 삭제하시겠습니까?")
                .setPositiveButton("yes",object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        val retrofitAPI = RetrofitClient.getInstance().create(BoardService::class.java)
                        retrofitAPI.deleteBoard(board_num).enqueue(object : Callback<Void>{
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if(response.isSuccessful) {
                                    Toast.makeText(
                                        this@BoardDetailActivity,
                                        "게시글 삭제 성공",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
                                }else{
                                    Toast.makeText(this@BoardDetailActivity,"게시글 삭제 실패", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Toast.makeText(this@BoardDetailActivity,"서버 오류 발생", Toast.LENGTH_SHORT).show()
                            }

                        })
                    }

                })
                .show()
        }
    }
    fun requestList() : List<BoardDetailResponse.Comment>{
        setBoardDetail(board_code,board_num)
        return commentList
    }

    private fun checkSameUser(writeUserId : Int){
        if(writeUserId == userInfo.getInt("user_id",0)){
            binding.btnSameUser.visibility = View.VISIBLE
        }
    }

    private fun setBoardDetail(code : Int , num : Int){
        val userId = userInfo.getInt("user_id",0)
        val retrofitAPI = RetrofitClient.getInstance().create(BoardService::class.java)

        retrofitAPI.getBoardDetail(userId,code,num).enqueue( object : Callback<BoardDetailResponse> {
            override fun onResponse(
                call: Call<BoardDetailResponse>,
                response: Response<BoardDetailResponse>,
            ) {
                if(response.isSuccessful){
                    val res = response.body()!!
                    val textView = binding.boardDetailContent
                    boardTitle = res.title
                    binding.boardDetailTitle.text = res.title
                    MainApplication.setOtherUserProfilePK(res.userId.toLong(),binding.ciProfileImage)
                    boardContent = res.content
                    textView.setHtml(res.content,HtmlHttpImageGetter(textView))
                    binding.visited.text = "조회수 : ${res.visited}"
                    binding.commentCount.text = "(${res.comment.size})"
                    binding.userName.text = res.userNickname
                    binding.writeDay.text = res.regTime
                    commentList = res.comment
                    checkSameUser(res.userId)
                }
            }
            override fun onFailure(call: Call<BoardDetailResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}


