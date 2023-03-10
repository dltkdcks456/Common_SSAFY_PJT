package com.android.rideway_app

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.databinding.MeetingListBinding
import com.android.rideway_app.databinding.MeetingListMainBinding
import com.android.rideway_app.mainapp.MainApplication
import com.android.rideway_app.meeting.CommunityDetailActivity
import com.android.rideway_app.meeting.CommunityUpdateActivity
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.meeting.CommunityListResponse
import com.android.rideway_app.retrofit.meeting.CommunityService
import com.android.rideway_app.retrofit.meeting.SignUpData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MeetingListAdapter(val data : List<CommunityListResponse.Content>) : RecyclerView.Adapter<MeetingListAdapter.MyViewHolder>() {
    private val retrofitAPI = RetrofitClient.getInstance().create(CommunityService::class.java)
    inner class MyViewHolder(binding : MeetingListMainBinding) : RecyclerView.ViewHolder(binding.root){
        var isSignUp = true
        var bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding : MeetingListMainBinding = MeetingListMainBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val content = data[position]
            holder.isSignUp = async(Dispatchers.IO) {
                retrofitAPI.checkUserCoroutine(content.communityId.toString())
            }.await()
            val userId = MainApplication.getUserId()
            holder.bind.title.text = content.title
            holder.bind.place.text = "${content.si} ${content.gun} ${content.dong}"
            holder.bind.date.text = content.date
            holder.bind.present.text = "${content.currentPerson}???"
            holder.bind.max.text = "${content.maxPerson}???"
            checkSignUp(holder,content.communityId.toString())
            //?????? ?????? ??????
            if (content.userId.toInt() == userId) {
                holder.bind.sameUserLayout.visibility = View.VISIBLE
                holder.bind.otherUserLayout.visibility = View.GONE
            } else {
                holder.bind.sameUserLayout.visibility = View.GONE
                holder.bind.otherUserLayout.visibility = View.VISIBLE
            }

            //?????? ?????? ??????
            if (!holder.isSignUp && (content.currentPerson == content.maxPerson)) {
                holder.bind.btnSignUp.visibility = View.GONE
                holder.bind.btnExceed.visibility = View.VISIBLE
            }

            //??????
            holder.bind.btnUpdate.setOnClickListener {
                val intent = Intent(holder.bind.root.context, CommunityUpdateActivity::class.java)
                intent.putExtra("communityId", content.communityId.toString())
                holder.bind.root.context.startActivity(intent)
            }

            //??????
            holder.bind.btnDelete.setOnClickListener {
                AlertDialog.Builder(holder.bind.root.context)
                    .setTitle("?????? ??????")
                    .setMessage("????????? ?????????????????????????")
                    .setPositiveButton("???") { p0, p1 ->
                        delCommunity(
                            holder,
                            content.communityId.toString()
                        )
                    }
                    .setNegativeButton("?????????", null).show()
            }

            //??????
            holder.bind.btnSignUp.setOnClickListener {
                if (holder.isSignUp) withdraw(holder, content.communityId.toString())
                else signUp(holder, content.communityId.toString())
            }

            //??????
            holder.bind.title.setOnClickListener {
                val intent = Intent(holder.bind.root.context, CommunityDetailActivity::class.java)
                intent.putExtra("communityId", content.communityId.toString())
                intent.putExtra("hostUser", (userId == content.userId.toInt()))
                intent.putExtra("participate", holder.isSignUp)
                holder.bind.root.context.startActivity(intent)
            }
        }

    }
    private fun checkSignUp(holder : MyViewHolder, communityId : String) {
        retrofitAPI.checkUser(communityId).enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    holder.isSignUp = response.body()!!
                    setBtnText(holder)
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {}
        })
    }

    private fun signUp(holder: MyViewHolder,communityId: String){
        retrofitAPI.addUser(SignUpData(communityId,MainApplication.prefs.getString("token",""))).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    Toast.makeText(holder.bind.root.context,"?????? ??????",Toast.LENGTH_SHORT).show()
                    holder.isSignUp = !holder.isSignUp
                    setBtnText(holder)
                }else{
                    Toast.makeText(holder.bind.root.context,"????????? ?????????????????????.",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(holder.bind.root.context,"???????????? ??????",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun withdraw(holder: MyViewHolder,communityId: String){
        retrofitAPI.delUser(SignUpData(communityId,MainApplication.prefs.getString("token",""))).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    Toast.makeText(holder.bind.root.context,"?????? ??????",Toast.LENGTH_SHORT).show()
                    holder.isSignUp = !holder.isSignUp
                    setBtnText(holder)
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(holder.bind.root.context,"???????????? ??????",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setBtnText(holder : MyViewHolder){
        holder.bind.btnSignUp.text = if(holder.isSignUp) "??????" else "??????"
    }


    private fun delCommunity(holder:MyViewHolder,communityId: String){
        val data = hashMapOf<String,String>()
        data.put("token",MainApplication.getUserToken())
        data.put("community_id",communityId)

        retrofitAPI.deleteCommunity(data).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    Toast.makeText(holder.bind.root.context,"?????? ??????",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(holder.bind.root.context,"?????? ??????",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(holder.bind.root.context,"???????????? ??????",Toast.LENGTH_SHORT).show()
            }

        })
    }

}