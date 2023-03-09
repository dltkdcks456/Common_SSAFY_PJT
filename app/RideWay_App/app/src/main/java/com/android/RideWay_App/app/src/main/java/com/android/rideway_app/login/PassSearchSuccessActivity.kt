package com.android.rideway_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.rideway_app.databinding.ActivityPassSearchSuccessBinding

class PassSearchSuccessActivity : AppCompatActivity() {
    lateinit var binding:ActivityPassSearchSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassSearchSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIdSearchLogin.setOnClickListener {
            val intent = Intent(this@PassSearchSuccessActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}