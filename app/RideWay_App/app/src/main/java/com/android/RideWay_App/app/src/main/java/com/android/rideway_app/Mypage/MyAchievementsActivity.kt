package com.android.rideway_app.Mypage

import android.app.Activity
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.rideway_app.databinding.ActivityMyAchievementsBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.login.TokenData
import com.android.rideway_app.retrofit.myProfile.MyAchievementsData
import com.android.rideway_app.retrofit.myProfile.MyProfileService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAchievementsActivity : AppCompatActivity() {
    lateinit var binding : ActivityMyAchievementsBinding
    lateinit var userProfile: SharedPreferences
    private var data = mapOf(
        0 to mutableListOf(false, false, false),
        1 to mutableListOf(false, false, false),
        2 to mutableListOf(false, false, false),
        3 to mutableListOf(false, false, false),
        4 to mutableListOf(false, false, false),
    )

    override fun onResume() {
        super.onResume()
        // 유저 프로파일 가져옴
        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)
        update(userProfile.getString("token",null)!!)
        getMyAchievement(userProfile.getString("nickname",null)!!)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAchievementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibBack.setOnClickListener {
            finish()
        }

    }

    private fun update(token : String){
        val retrofitAPI = RetrofitClient.getInstance().create(MyProfileService::class.java)

        retrofitAPI.updateMyAchievement(TokenData(token)).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }

    private fun getMyAchievement(nickname : String){
        val retrofitAPI = RetrofitClient.getInstance().create(MyProfileService::class.java)

        retrofitAPI.getMyAchievement(nickname).enqueue(object : Callback<MyAchievementsData> {
            override fun onResponse(call: Call<MyAchievementsData>, response: Response<MyAchievementsData>) {
                if(response.isSuccessful && response.body() != null) {

                    for (item in response.body()!!){
                        data[(item.achievement_id-1)/3]!![(item.achievement_id-1)%3] = true
                    }

                    binding.rvAchievements.apply {
                        adapter = AchievementAdapter(data)
                        layoutManager = LinearLayoutManager(context)
                        setHasFixedSize(true)
                    }
                }
                else{
                    binding.rvAchievements.apply {
                        adapter = AchievementAdapter(data)
                        layoutManager = LinearLayoutManager(context)
                        setHasFixedSize(true)
                    }
                }
            }
            override fun onFailure(call: Call<MyAchievementsData>, t: Throwable) {
            }
        })
    }
}