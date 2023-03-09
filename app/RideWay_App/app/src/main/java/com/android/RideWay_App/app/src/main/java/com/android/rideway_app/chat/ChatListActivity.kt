package com.android.rideway_app.chat

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.android.rideway_app.Mypage.ProfileSet
import com.android.rideway_app.databinding.ActivityChatListBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.chat.*
import com.android.rideway_app.retrofit.fileUpload.FileService
import com.google.gson.Gson
import io.jenetics.jpx.WayPoint
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage
import java.io.IOException
import java.util.*
import kotlin.math.abs
import kotlin.math.atan

/*
* 해야 하는 것
* 먼저 모든 채팅룸을 가져온다. -> api가 있음 액티비티에서 1초마다 가져온다.
* 모든 룸을 가져왔다면 해당하는 모든 룸에 subscribe해서 그 값을 adapter에 보내줌
* 그래서 adpater는 그 내용을 바탕으로 값의 변화를 표시해줌
* 그리고 클릭하면 해당 채팅룸으로 intent해서 대화가 가능하게 만든다.
*
* */

class ChatListActivity : AppCompatActivity() {
    lateinit var binding : ActivityChatListBinding
    lateinit var userProfile: SharedPreferences
    lateinit var mutableList : ChatRoomListResponseData
    lateinit var timer: Timer

    lateinit var userNickname : String

    override fun onResume() {
        super.onResume()
//        timer = Timer(true)
        runOnUiThread {
//            CheckMyChatroom()
            getMyChatRoomList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 유저 프로파일 가져옴
        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        // 닉네임 가져옴
        userNickname = userProfile.getString("nickname","가나다")!!


        binding.ibBack.setOnClickListener {
            finish()
        }



    }

//    private fun CheckMyChatroom() {
//        val task: TimerTask = object : TimerTask() {
//            override fun run() {
//                getMyChatRoomList()
//                Log.d("timerTest", "2초마다 가져오나요?")
//            }
//        }
//        timer.schedule(task, 0,2000)
//    }

    private fun getMyChatRoomList(){
        val retrofitAPI = RetrofitClient.getInstance().create(ChatService::class.java)

        retrofitAPI.getMyChatroom(userProfile.getString("token",null)!!).enqueue(object :
            Callback<ChatRoomListResponseData> {
            override fun onResponse(call: Call<ChatRoomListResponseData>, response: Response<ChatRoomListResponseData>) {
                if(response.isSuccessful && response.body() != null){
                    mutableList = response.body()!!
                    var rvAdpater = ChatListAdapter(mutableList, userProfile.getInt("user_id",1))


                    // 길게 클릭했을 때
                    rvAdpater.setItemLongClickListener(object : ChatListAdapter.OnItemLongClickListener{

                        override fun onLongClickListener(v: View, position: Int, chatItem: ChatRoomListResponseDataItem): Boolean {
                            AlertDialog.Builder(this@ChatListActivity)
                                .setTitle("채팅 방 나가기")
                                .setMessage("\n채팅방에서 나가시겠습니까?")
                                .setPositiveButton("확인") { _, _ ->
                                    getOutRoom(chatItem.chattingRoomId)
                                }
                                .create()
                                .show()
                            return true
                        }

                    })

                    // 그냥 클릭했을 때
                    rvAdpater.setItemClickListener(object : ChatListAdapter.OnItemClickListener{
                        override fun onClick( v: View,  position: Int,  chatItem: ChatRoomListResponseDataItem) {
                            val intent = Intent(this@ChatListActivity, ChatActivity::class.java)
                            intent.putExtra("chatRoomId",chatItem.chattingRoomId)
                            intent.putExtra("chatType",chatItem.type)
                            startActivity(intent)
                        }

                    })

                    binding.recyclerMessagesList.adapter = rvAdpater
                }
            }
            override fun onFailure(call: Call<ChatRoomListResponseData>, t: Throwable) {
            }
        })
    }

    private fun getOutRoom(roomId: String){
        val retrofitAPI = RetrofitClient.getInstance().create(ChatService::class.java)

        retrofitAPI.deleteChat( DeleteChatData(userProfile.getString("nickname",null)!!, roomId)).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful && response.body() != null){
                    getMyChatRoomList()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }

    override fun onPause() {
        super.onPause()
//        timer.cancel()
    }

}