package com.android.rideway_app.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.R
import com.android.rideway_app.databinding.ChatListBinding
import com.android.rideway_app.databinding.MychatBinding
import com.android.rideway_app.databinding.OtherchatBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.chat.*
import com.android.rideway_app.retrofit.login.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/*
* userId를 받기 때문에 만약 개별 채팅일 경우 상대방 아이디를 api를 통해서 받으면 된다. => gerRoomUser api를 사용하여 가져오면 됨
* 개별이면 리턴 값이 배열로 되있는 userId + userNickname으로 되있는데 내께 아닌 다른 놈 꺼를 넣어주면 됨
*
*
* */

class ChatListAdapter(var data : ChatRoomListResponseData, var user_id : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return myChatList(ChatListBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    inner class myChatList(binding : ChatListBinding) : RecyclerView.ViewHolder(binding.root){
        var bind = binding
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data!![position].type
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var content : ChatRoomListResponseDataItem = data[position]

        if (viewHolder is myChatList){
            if(content.type==99){
                viewHolder.bind.tvChatTitle.text = content.name
                getLastMessage(content.chattingRoomId, viewHolder.bind)
            }
            else{
                viewHolder.bind.tvChatTitle.text = "개별 채팅"
                getLastMessage(content.chattingRoomId, viewHolder.bind)
                getChatUsers(content.chattingRoomId, viewHolder.bind)

            }

        }

        viewHolder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position, content)
        }

        viewHolder.itemView.setOnLongClickListener {
            onItemLongClickListener.onLongClickListener(it, position, content)
        }

    }

    // (2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, chatItem : ChatRoomListResponseDataItem)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener

    interface OnItemLongClickListener{
        fun onLongClickListener(v: View, position: Int, chatItem : ChatRoomListResponseDataItem) : Boolean
    }

    fun setItemLongClickListener(onItemLongClickListener: OnItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener
    }


    private lateinit var onItemLongClickListener : OnItemLongClickListener


    private fun getLastMessage(chatRoomId : String, bind: ChatListBinding){
        val retrofitAPI = RetrofitClient.getInstance().create(ChatService::class.java)

        retrofitAPI.getChatHistory(chatRoomId).enqueue(object : Callback<ChatHistory> {
            override fun onResponse(call: Call<ChatHistory>, response: Response<ChatHistory>) {
                if(response.isSuccessful && response.body()!!.size>0){

                    // 여기서 받아온 String 형태의 date 값을 실제 LocalDateTime으로 바꿔준다.
                    val string = response.body()!![response.body()!!.size-1].sendTime
                    val formatter = DateTimeFormatter.ISO_DATE_TIME
                    val date = LocalDateTime.parse(string, formatter)

                    // 여기서 그 LocalDateTime을 내가 원하는 형태의 date타입으로 바꾼다.
                    val timeFormatter = DateTimeFormatter.ofPattern("MM-dd hh:mm")
                    val dateTime = date.format(timeFormatter)

                    bind.tvLastMessageTime.text = dateTime
                    bind.tvLastMessage.text = response.body()!![response.body()!!.size-1].message
                }
                Log.d("아아아나ㅣ어라ㅣ너라ㅣ", chatRoomId + "   " + response.body()!!)
            }
            override fun onFailure(call: Call<ChatHistory>, t: Throwable) {
            }
        })
    }
    private fun getChatUsers(chatRoomId : String, bind: ChatListBinding){
        val retrofitAPI = RetrofitClient.getInstance().create(ChatService::class.java)

        retrofitAPI.getChatUsers(chatRoomId).enqueue(object : Callback<ChatRoomUsers> {
            override fun onResponse(call: Call<ChatRoomUsers>, response: Response<ChatRoomUsers>) {
                if(response.isSuccessful){
                    for(i : Int in 0..response.body()!!.size-1){
                        if(response.body()!![i].userId != user_id){
                            bind.tvChatTitle.text = response.body()!![i].nickname
                        }
                    }
                }
                else{
                }
            }
            override fun onFailure(call: Call<ChatRoomUsers>, t: Throwable) {
            }
        })
    }
}