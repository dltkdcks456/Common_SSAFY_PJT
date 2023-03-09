package com.android.rideway_app.chat

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.android.rideway_app.databinding.ActivityChatBinding
import com.android.rideway_app.databinding.ChatListBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.chat.*
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/*
* 지금 구현이 안되어있는 부분들 리스트
*  해결!) 채팅 룸을 만들고 채팅이 되는지 확인 -> 현재 api에서의 채팅이 돌아가는지 확인한다는 뜻
*  2) 채팅 목록을 만들어야 함
*  3) 채팅 이미지 가져오기 확인
*
* 원래 로직은 1대1 채팅을 신청하면 채팅룸을 만들고, 채팅창 액티비티 보내서 채팅 치게 하고, 뒤로가기 누르면 채팅 리스트로 인텐트하게 만들면 될 듯
* 일단 채팅 룸 만들기부터 시작하자
*
* 개발 순서
* 1) -> 채팅룸을 만들고 그게 채팅 목록에 뜨는지 확인한다.
* 1을 하기 위한 프로세스 -> 채팅 목록을 만든다. 버튼을 둬서 api에 채팅 룸 만드는걸 실행시킨다. 그
*
*
*
*
* */


class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    lateinit var userProfile: SharedPreferences
    lateinit var mStompClient : StompClient
    lateinit var mutableList : MutableList<ChatResponseData>

    lateinit var chatRoomId : String
    lateinit var chatType : String
    lateinit var userNickname : String

    var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 유저 프로파일 가져옴
        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        // 닉네임이랑 여기 채팅 방 아이디 가져옴
        chatRoomId = intent.getStringExtra("chatRoomId").toString()
        if(intent.getIntExtra("chatType",99)==99) chatType = "queue"
        else chatType = "topic"
        userNickname = userProfile.getString("nickname","가나다")!!

        // 채팅창에 표시할 리스트
        mutableList =  mutableListOf<ChatResponseData>()
        binding.recyclerMessages.adapter = ChatAdapter(mutableList, userProfile.getInt("user_id",1))
        getLastMessage(chatRoomId)

        // stomp 주소 설정 및 연결을 해줌
//        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.0.2.2:8080/api/ws-stomp/websocket")
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://i8e102.p.ssafy.io/api/ws-stomp/websocket")
        mStompClient.connect()


//        mStompClient.lifecycle().subscribe { lifecycleEvent: LifecycleEvent ->
//            when (lifecycleEvent.type) {
//                LifecycleEvent.Type.OPENED -> {
//                }
//                LifecycleEvent.Type.ERROR -> Log.e("ABC", "Error", lifecycleEvent.exception)
//                LifecycleEvent.Type.CLOSED -> Log.d("ABC", "Stomp connection closed")
//
//                else -> {}
//            }
//        }

        // 리사이클러 뷰 어댑터 설정함



        binding.ibBack.setOnClickListener {
            finish()
        }

        mStompClient.topic("/${chatType}/chat/room/${chatRoomId}").subscribe { topicMessage: StompMessage ->
            Log.d("asdasdasd","@!#!@   " + topicMessage.payload )
            val returnMessage = gson.fromJson(topicMessage.payload, ChatResponseData::class.java)
            runOnUiThread {
                messageChange(returnMessage)
            }
        }

        binding.etMessagePart.addTextChangedListener {
            binding.btnSendMessage.isEnabled = !binding.etMessagePart.text.isEmpty()
        }

        binding.btnSendMessage.setOnClickListener {
            if(intent.getIntExtra("chatType",99)==1){
                mStompClient.send("/app/chat/message", gson.toJson(ChatData(userNickname,"1",chatRoomId, binding.etMessagePart.text.toString()))).subscribe()
            }
            else mStompClient.send("/app/chat/messageToCommunity", gson.toJson(ChatData(userNickname,"1",chatRoomId, binding.etMessagePart.text.toString()))).subscribe()
            binding.etMessagePart.text = null
        }

    }

    fun messageChange(value : ChatResponseData){
        mutableList.add(value)
        binding.recyclerMessages.adapter!!.notifyItemChanged(mutableList.size)
        binding.recyclerMessages.scrollToPosition(mutableList.size-1)

    }

    private fun getLastMessage(chatRoomId : String){
        val retrofitAPI = RetrofitClient.getInstance().create(ChatService::class.java)

        retrofitAPI.getChatHistory(chatRoomId).enqueue(object : Callback<ChatHistory> {
            override fun onResponse(call: Call<ChatHistory>, response: Response<ChatHistory>) {
                if(response.isSuccessful && response.body() != null){


                    for(idx : Int in 0..response.body()!!.size-1){
                        mutableList.add(ChatResponseData( response.body()!![idx].sender_id, 1, response.body()!![idx].sender_nickname, chatRoomId,
                            response.body()!![idx].message, response.body()!![idx].sendTime))

                    }
                    binding.recyclerMessages.scrollToPosition(mutableList.size-1)
                }
            }
            override fun onFailure(call: Call<ChatHistory>, t: Throwable) {
            }
        })
    }


}