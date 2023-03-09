package com.android.rideway_app.Mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.rideway_app.databinding.ActivityMyFavoriteBinding
import com.android.rideway_app.databinding.ActivityMyPostBinding
import com.android.rideway_app.mainapp.MainApplication
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.myProfile.MyProfileService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.create

class MyPostActivity : AppCompatActivity() {
    lateinit var binding : ActivityMyPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ibBack.setOnClickListener {
            finish()
        }
        getList()
    }

    override fun onResume() {
        super.onResume()
        getList()
    }

    private fun getList(){
        CoroutineScope(Dispatchers.IO).launch{
            val list = RetrofitClient.getInstance().create(MyProfileService::class.java).getBoardList(MainApplication.prefs.getString("nickname",""))
            launch(Dispatchers.Main) {
                binding.recyclerView.adapter = MyBoardListAdapter(list)
                binding.recyclerView.layoutManager = LinearLayoutManager(this@MyPostActivity)
            }
        }
    }
}