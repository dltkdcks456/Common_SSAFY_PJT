package com.android.rideway_app.meeting

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.rideway_app.chat.ChatActivity
import com.android.rideway_app.databinding.ActivityCommunityDetailBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.chat.*
import com.android.rideway_app.retrofit.login.LoginService
import com.android.rideway_app.retrofit.login.UserDataResponse
import com.android.rideway_app.retrofit.meeting.CommunityDetailResponse
import com.android.rideway_app.retrofit.meeting.CommunityService
import com.android.rideway_app.retrofit.meeting.ParticipantDataResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityDetailActivity : AppCompatActivity() ,startFunc{
    private lateinit var binding: ActivityCommunityDetailBinding
    private lateinit var communityId : String
    private var host : Boolean = false
    lateinit var userProfile: SharedPreferences
    private val retrofitAPI = RetrofitClient.getInstance().create(CommunityService::class.java)
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)
        communityId = intent.getStringExtra("communityId")!!
        host = intent.getBooleanExtra("hostUser",false)
        binding.btnParticipate.setOnClickListener {
            if(binding.participateUserList.visibility == View.VISIBLE){
                binding.participateUserList.visibility = View.GONE
                binding.ivOpen.animate().apply {
                    duration = 200
                    rotation(0f)
                }
            }else{
                binding.participateUserList.visibility = View.VISIBLE
                binding.ivOpen.animate().apply {
                    duration = 200
                    rotation(180f)
                }
            }
        }

        binding.btnBanUser.setOnClickListener {
            if(binding.banUserList.visibility == View.VISIBLE){
                binding.banUserList.visibility = View.GONE
                binding.ivOpen1.animate().apply {
                    duration = 200
                    rotation(0f)
                }
            }else{
                binding.banUserList.visibility = View.VISIBLE
                binding.ivOpen1.animate().apply {
                    duration = 200
                    rotation(180f)
                }
            }
        }
        if(!host){
            binding.hostUserLayout.visibility = View.GONE
        }

        binding.etSearchKeyword.addTextChangedListener {
            coroutineScope.launch {
                //IO 쓰레드에서 네트워크 작업 수행
                val list = async(Dispatchers.IO) {
                    getSearchUser()
                }
                //await 키워드는 결과값 받기 전까지 쓰레드를 일시정지
                binding.userSearchRecyclerView.adapter = UserListAdapter(this@CommunityDetailActivity,communityId,list.await())
                binding.userSearchRecyclerView.layoutManager = LinearLayoutManager(this@CommunityDetailActivity)
                getDetail()
                getUserList()
            }
        }
        if(intent.getBooleanExtra("participate",false)){
            binding.btnChatRoom.visibility = View.VISIBLE
            binding.btnChatRoom.setOnClickListener {
                getRoomId()
            }
        }else{
            binding.btnChatRoom.visibility = View.INVISIBLE
        }
        getDetail()
        getUserList()
        getBanList()
    }

    private suspend fun getSearchUser(): List<UserDataResponse> {
        val retrofitUser = RetrofitClient.getInstance().create(LoginService::class.java)
        return retrofitUser.searchUser(binding.etSearchKeyword.text.toString())
    }

    private fun getDetail(){

        retrofitAPI.getDetail(communityId).enqueue(object : Callback<CommunityDetailResponse>{
            override fun onResponse(
                call: Call<CommunityDetailResponse>,
                response: Response<CommunityDetailResponse>
            ) {
                if(response.isSuccessful){
                    val content = response.body()!!
                    binding.content.text = content.content
                    binding.date.text = content.date
                    binding.place.text = "${content.si} ${content.gun} ${content.dong}"
                    binding.max.text = "최대 ${content.max_person}"
                    binding.current.text = "현재 ${content.current_person}"
                }
            }

            override fun onFailure(call: Call<CommunityDetailResponse>, t: Throwable) {
                Toast.makeText(this@CommunityDetailActivity,"네트워크 오류",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getUserList(){
        retrofitAPI.getCommunityUsers(communityId).enqueue(object : Callback<List<ParticipantDataResponse>>{
            override fun onResponse(
                call: Call<List<ParticipantDataResponse>>,
                response: Response<List<ParticipantDataResponse>>
            ) {
                println(response.body())
                if(response.isSuccessful){
                    val list = response.body()!!
                    val adapter = ParticipantListAdapter(this@CommunityDetailActivity,communityId,list,intent.getBooleanExtra("hostUser",false))
                    binding.participateRecyclerView.adapter = adapter
                    binding.participateRecyclerView.layoutManager = LinearLayoutManager(this@CommunityDetailActivity)
                }
            }
            override fun onFailure(call: Call<List<ParticipantDataResponse>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun getBanList(){
        retrofitAPI.banList(communityId).enqueue(object : Callback<List<String>>{
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>,
            ) {
                if(response.isSuccessful){
                    println(response.body())
                    val adapter = BanListAdapter(this@CommunityDetailActivity,communityId,response.body()!!)
                    binding.banRecyclerView.adapter = adapter
                    binding.banRecyclerView.layoutManager = LinearLayoutManager(this@CommunityDetailActivity)
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {

            }

        })
    }

    private fun getRoomId(){

        retrofitAPI.getDetail(communityId).enqueue(object : Callback<CommunityDetailResponse>{
            override fun onResponse(
                call: Call<CommunityDetailResponse>,
                response: Response<CommunityDetailResponse>
            ) {
                if(response.isSuccessful){
                    val content = response.body()!!
                    enterChatRoom(content.chatting_room_id)
                }
            }

            override fun onFailure(call: Call<CommunityDetailResponse>, t: Throwable) {
                Toast.makeText(this@CommunityDetailActivity,"네트워크 오류",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun enterChatRoom(chatRoomId:String){

        val retrofitAPI = RetrofitClient.getInstance().create(ChatService::class.java)

        retrofitAPI.enterCommunityChat(ChatCommunityData(userProfile.getString("nickname","가나다")!!,chatRoomId))
            .enqueue(object : Callback<ChatCommunityEnterResponseData> {
            override fun onResponse(call: Call<ChatCommunityEnterResponseData>, response: Response<ChatCommunityEnterResponseData>) {
                if(response.isSuccessful && response.body() != null){
                    val intent = Intent(this@CommunityDetailActivity, ChatActivity::class.java)
                    intent.putExtra("chatRoomId", response.body()!!.chattingRoomId)
                    intent.putExtra("chatType", 99)
                    startActivity(intent)
                }
            }
            override fun onFailure(call: Call<ChatCommunityEnterResponseData>, t: Throwable) {
            }
        })
    }

    override fun userList() {
        getDetail()
        getUserList()
        getBanList()
    }
}