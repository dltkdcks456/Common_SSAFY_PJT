package com.android.rideway_app

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.rideway_app.Mypage.*
import com.android.rideway_app.chat.ChatListActivity
import com.android.rideway_app.databinding.FragmentMypageBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.weeklyUser.UserRecordData
import com.android.rideway_app.retrofit.weeklyUser.WeeklyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment() {
    lateinit var container : ViewGroup
    lateinit var binding: FragmentMypageBinding
    private lateinit var userProfile: SharedPreferences
    private lateinit var mutableList: MutableList<UserRecordData>

    override fun onResume() {
        super.onResume()

        userProfile = container.context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        binding.ciProfileImage.setImageBitmap(ProfileSet.stringToBitmap(userProfile.getString("profileImage","null")))

        binding.tvMyFragNickName.text = userProfile.getString("nickname","오류 발생")

        binding.tvMyFragEmail.text = userProfile.getString("email","오류 발생")

        // 채팅 버튼
        binding.clBtnChatList.setOnClickListener {
            val intent = Intent(container.context, ChatListActivity::class.java)
            startActivity(intent)
        }

        // 프로필 수정 버튼
        binding.btnProfile.setOnClickListener {
            val intent = Intent(container.context, MypageActivity::class.java)
            startActivity(intent)
        }

        // 찜 목록 버튼
        binding.clBtnFavorite.setOnClickListener {
            val intent = Intent(container.context, MyFavoriteActivity::class.java)
            startActivity(intent)
        }

        // 내 게시물 버튼
        binding.clMyPost.setOnClickListener {
            val intent = Intent(container.context, MyPostActivity::class.java)
            startActivity(intent)
        }

        // 내 업적 버튼
        binding.clMyAchievements.setOnClickListener {
            val intent = Intent(container.context, MyAchievementsActivity::class.java)
            startActivity(intent)
        }

        // 내 판매 목록 버튼
        binding.clMySales.setOnClickListener {
            val intent = Intent(container.context, MySalesActivity::class.java)
            startActivity(intent)
        }

        mutableList = mutableListOf()
        mutableList.add(UserRecordData(-1,-1,-1,-1,-1,-1,-1,-1))
        getMyAllRecord()



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.container = container!!
        binding = FragmentMypageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun getMyAllRecord(){
        var retrofitAPI = RetrofitClient.getInstance().create(WeeklyService::class.java)

        Log.d("여기까지오냐?", "최종장 왔다")
        retrofitAPI.getUserRecord(userProfile.getString("nickname","오류 발생")!!).enqueue(object : Callback<UserRecordData> {
            override fun onResponse(call: Call<UserRecordData>, response: Response<UserRecordData>) {
                if(response.isSuccessful && response.body() != null){
                    mutableList.clear()
                    mutableList.add(response.body()!!)
                    mutableList.add(response.body()!!)
                    binding.rvMyData.apply {
                        adapter = MyRecordAdapter(mutableList)
                        layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.HORIZONTAL, false)
                    }
                }
            }

            override fun onFailure(call: Call<UserRecordData>, t: Throwable) {
                t.printStackTrace()
                binding.rvMyData.apply {
                    adapter = MyRecordAdapter(mutableList)
                    layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.HORIZONTAL, false)
                }
            }
        })

    }

    companion object {

    }
}