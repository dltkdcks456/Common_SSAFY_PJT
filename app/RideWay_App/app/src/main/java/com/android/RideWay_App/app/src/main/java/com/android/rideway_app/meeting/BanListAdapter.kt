package com.android.rideway_app.meeting

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.databinding.BanListBinding
import com.android.rideway_app.mainapp.MainApplication
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.meeting.CommunityService
import com.android.rideway_app.retrofit.meeting.ParticipantDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BanListAdapter(val func: startFunc,val communityId : String ,val data : List<String>): RecyclerView.Adapter<BanListAdapter.MyViewHolder>() {
    inner class MyViewHolder(binding: BanListBinding) : RecyclerView.ViewHolder(binding.root){
        var bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding : BanListBinding = BanListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val content = data[position]
        //이미지
        
        //닉네임
        holder.bind.nickName.text = content
        
        //클릭 이벤트
        holder.bind.kickAss.setOnClickListener {
            AlertDialog.Builder(holder.bind.root.context)
                .setTitle("유저 밴 해제")
                .setMessage("유저 밴을 해제하시겠습니까?")
                .setPositiveButton("네") { p0, p1 -> banCancelUser(holder,content,communityId) }
                .setNegativeButton("아니요",null).show()
        }
    }

    private fun banCancelUser(holder: BanListAdapter.MyViewHolder, nickname : String, communityId : String){
        val retrofitAPI = RetrofitClient.getInstance().create(CommunityService::class.java)
        val data = hashMapOf<String,String>()
        data.put("token", MainApplication.getUserToken())
        data.put("ban_user_nickname",nickname)
        data.put("community_id" ,communityId)
        retrofitAPI.banCancelUser(data).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    AlertDialog.Builder(holder.bind.root.context)
                        .setTitle("유저 밴 해제")
                        .setMessage("유저밴 해제")
                        .setPositiveButton("네",null).show()
                    func.userList()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {

            }

        })
    }

}
