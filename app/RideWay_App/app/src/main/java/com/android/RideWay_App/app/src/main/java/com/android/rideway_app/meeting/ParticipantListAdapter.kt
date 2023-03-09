package com.android.rideway_app.meeting

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.databinding.ParticipantListBinding
import com.android.rideway_app.mainapp.MainApplication
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.meeting.CommunityService
import com.android.rideway_app.retrofit.meeting.ParticipantDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParticipantListAdapter(val func : startFunc,val communityId : String ,val data : List<ParticipantDataResponse>, val isHost : Boolean) : RecyclerView.Adapter<ParticipantListAdapter.MyViewHolder>() {
    inner class MyViewHolder(binding : ParticipantListBinding) : RecyclerView.ViewHolder(binding.root){
        var bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding : ParticipantListBinding = ParticipantListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val content = data[position]
        //이미지
        MainApplication.setOtherUserProfileBy(content.image_path,holder.bind.profileImage)
        //이름
        holder.bind.nickName.text = content.nickname
        //host user
        if(isHost){
            holder.bind.kickAss.visibility = View.VISIBLE

            holder.bind.kickAss.setOnClickListener {
                AlertDialog.Builder(holder.bind.root.context)
                    .setTitle("유저 강퇴")
                    .setMessage("유저를 강퇴하시겠습니까?")
                    .setPositiveButton("네") { p0, p1 -> banUser(holder,content.nickname,communityId) }
                    .setNegativeButton("아니요",null).show()
            }
        }else {
            holder.bind.kickAss.visibility = View.GONE
        }

    }

    private fun banUser(holder: MyViewHolder, nickname : String , communityId : String){
        val retrofitAPI = RetrofitClient.getInstance().create(CommunityService::class.java)
        val data = hashMapOf<String,String>()
        data.put("token",MainApplication.getUserToken())
        data.put("ban_user_nickname",nickname)
        data.put("community_id" ,communityId)
        retrofitAPI.banUser(data).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    AlertDialog.Builder(holder.bind.root.context)
                        .setTitle("유저 강퇴 완료")
                        .setMessage("유저를 강퇴했습니다.")
                        .setPositiveButton("네",null).show()
                    func.userList()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {

            }

        })
    }
}