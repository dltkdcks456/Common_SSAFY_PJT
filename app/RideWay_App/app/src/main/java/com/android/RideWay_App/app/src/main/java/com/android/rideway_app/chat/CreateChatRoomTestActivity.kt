package com.android.rideway_app.chat

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.rideway_app.MainActivity
import com.android.rideway_app.databinding.ActivityCreateChatRoomTestBinding
import com.android.rideway_app.login.LoginActivity
import com.android.rideway_app.login.PassSearchSuccessActivity
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.chat.ChatRoomResponseData
import com.android.rideway_app.retrofit.chat.ChatService
import com.android.rideway_app.retrofit.chat.CreateChatRoomData
import com.android.rideway_app.retrofit.login.LoginService
import com.android.rideway_app.retrofit.login.PassSearchData
import com.android.rideway_app.retrofit.login.StringResultResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateChatRoomTestActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateChatRoomTestBinding
    lateinit var userProfile: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateChatRoomTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        binding.btnCreateChat.setOnClickListener {
            createChat()
//            val intent = Intent(this@CreateChatRoomTestActivity, ChatActivity::class.java)
//            intent.putExtra("chatRoomId","a01f674f-46a8-488c-9d76-2b27287aadba")
//            startActivity(intent)

        }

    }

    private fun createChat(){
        val retrofitAPI = RetrofitClient.getInstance().create(ChatService::class.java)
        retrofitAPI.createDealChatRoom(CreateChatRoomData(userProfile.getString("token","sd")!!,
            userProfile.getString("nickname","가나다")!!,"레어닉")).enqueue(object :
            Callback<ChatRoomResponseData> {
            override fun onResponse(call: Call<ChatRoomResponseData>, response: Response<ChatRoomResponseData>) {
                if(response.isSuccessful){

                    val intent = Intent(this@CreateChatRoomTestActivity, ChatActivity::class.java)
                    intent.putExtra("chatRoomId", response.body()!!.roomId)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<ChatRoomResponseData>, t: Throwable) {
                Toast.makeText(this@CreateChatRoomTestActivity, "네트워크 접속 오류가 발생하였습니다.\n잠시후 다시 시도해주세요", Toast.LENGTH_LONG).show()
            }
        })

    }

}