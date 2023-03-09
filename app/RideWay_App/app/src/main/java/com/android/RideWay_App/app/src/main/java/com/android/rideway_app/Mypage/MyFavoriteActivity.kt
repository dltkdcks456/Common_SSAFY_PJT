package com.android.rideway_app.Mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.rideway_app.databinding.ActivityMyFavoriteBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.myProfile.MyProfileService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.create

class MyFavoriteActivity : AppCompatActivity() {
    lateinit var binding : ActivityMyFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyFavoriteBinding.inflate(layoutInflater)
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
        CoroutineScope(Dispatchers.IO).launch {
            val list = RetrofitClient.getInstance().create(MyProfileService::class.java).getZzimList()

            launch(Dispatchers.Main) {
                binding.recyclerView.adapter = ZzimListAdapter(list)
                binding.recyclerView.layoutManager = LinearLayoutManager(this@MyFavoriteActivity)
            }
        }
    }
}