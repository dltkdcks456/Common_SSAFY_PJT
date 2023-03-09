package com.android.rideway_app.Mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.rideway_app.databinding.ActivityDeleteSuccessBinding
import com.android.rideway_app.login.EntranceActivity

class DeleteSuccessActivity : AppCompatActivity() {
    lateinit var binding: ActivityDeleteSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDeleteAccountConfirm.setOnClickListener {
            val intent = Intent(this@DeleteSuccessActivity, EntranceActivity::class.java)
            startActivity(intent)
            finish()
        }

    }



}