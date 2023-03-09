package com.android.rideway_app.meeting

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.databinding.InviteListBinding
import com.android.rideway_app.databinding.ParticipantListBinding
import com.android.rideway_app.mainapp.MainApplication
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.fileUpload.FileService
import com.android.rideway_app.retrofit.login.LoginService
import com.android.rideway_app.retrofit.login.UserDataResponse
import com.android.rideway_app.retrofit.meeting.CommunityService
import com.android.rideway_app.retrofit.meeting.ParticipantDataResponse
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.io.InputStream

class UserListAdapter(val func: startFunc,val communityId : String, val data : List<UserDataResponse>) : RecyclerView.Adapter<UserListAdapter.MyViewHolder>() {
    inner class MyViewHolder(binding : InviteListBinding) : RecyclerView.ViewHolder(binding.root){
        var bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding : InviteListBinding = InviteListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val content = data[position]
        //이미지 코루틴 활용
        MainApplication.setOtherUserProfile(content.id,holder.bind.profileImage)

        //이름
        holder.bind.nickName.text = content.nickname

        holder.bind.btnInvite.setOnClickListener {
            AlertDialog.Builder(holder.bind.root.context)
                .setTitle("유저 초대")
                .setMessage("유저를 초대하시겠습니까?")
                .setPositiveButton("네") { p0, p1 -> inviteUser(holder,communityId,content.nickname) }
                .setNegativeButton("아니요",null).show()
        }
    }

    private fun inviteUser(holder: MyViewHolder,communityId : String,nickname : String){
        val retrofitAPI = RetrofitClient.getInstance().create(CommunityService::class.java)
        val data = hashMapOf<String,String>()
        data.put("token",MainApplication.getUserToken())
        data.put("invited_user_nickname",nickname)
        data.put("community_id" ,communityId)
        retrofitAPI.inviteUser(data).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    AlertDialog.Builder(holder.bind.root.context)
                        .setTitle("유저 초대 완료")
                        .setMessage("유저를 초대했습니다.")
                        .setPositiveButton("네",null).show()
                    func.userList()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {

            }

        })

    }
}