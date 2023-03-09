package com.android.rideway_app.Mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.rideway_app.databinding.ActivityMySalesBinding

class MySalesActivity : AppCompatActivity() {
    lateinit var binding : ActivityMySalesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMySalesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}