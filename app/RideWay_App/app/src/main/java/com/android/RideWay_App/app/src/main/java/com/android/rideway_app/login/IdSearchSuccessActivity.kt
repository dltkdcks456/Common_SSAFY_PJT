package com.android.rideway_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.rideway_app.databinding.ActivityIdSearchSuccessBinding

class IdSearchSuccessActivity : AppCompatActivity() {
    lateinit var binding : ActivityIdSearchSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityIdSearchSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val findedId = intent.getStringExtra("result")

        binding.tvFindedId.text = "아이디${findedId}"

        binding.btnIdSearchLogin.setOnClickListener {
            finish()
        }

        binding.btnIdSearchPass.setOnClickListener {
            val intent = Intent(this@IdSearchSuccessActivity, PassSearchActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}