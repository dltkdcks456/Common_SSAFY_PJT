package com.android.rideway_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.rideway_app.databinding.ActivityLoginSuccessBinding

class LoginSuccessActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvResponse.text = intent.getStringExtra("response")

    }
}