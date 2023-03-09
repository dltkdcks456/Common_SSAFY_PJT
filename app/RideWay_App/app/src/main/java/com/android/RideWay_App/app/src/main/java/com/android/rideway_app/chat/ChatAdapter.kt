package com.android.rideway_app.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.databinding.MychatBinding
import com.android.rideway_app.databinding.OtherchatBinding
import com.android.rideway_app.retrofit.chat.ChatResponseData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatAdapter(var data : List<ChatResponseData>, var user_id : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("타입" , "아니 뭐임")
        return if(viewType==user_id){
            myChat(MychatBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
        else{
            otherChat(OtherchatBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }

    inner class myChat(binding : MychatBinding) : RecyclerView.ViewHolder(binding.root){
        var bind = binding
    }

    inner class otherChat(binding : OtherchatBinding) : RecyclerView.ViewHolder(binding.root){
        var bind = binding
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return data!![position].sender.toInt()
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var content : ChatResponseData = data[position]

        // 여기서 받아온 String 형태의 date 값을 실제 LocalDateTime으로 바꿔준다.
        val string = content.sendTime
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val date = LocalDateTime.parse(string, formatter)

        // 여기서 그 LocalDateTime을 내가 원하는 형태의 date타입으로 바꾼다.
        val timeFormatter = DateTimeFormatter.ofPattern("MM-dd\na hh:mm")
        val dateTime = date.format(timeFormatter)

        if (viewHolder is myChat) {
            viewHolder.bind.tvChatMessage.text = content.message
            viewHolder.bind.tvChatDate.text = dateTime.toString()
        } else if(viewHolder is otherChat){
            viewHolder.bind.tvOtherNickName.text = content.senderNickName
            viewHolder.bind.tvChatMessage.text = content.message
            viewHolder.bind.tvChatDate.text = dateTime.toString()
        }

    }

}