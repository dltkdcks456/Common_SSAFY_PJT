package com.android.rideway_app.Mypage

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.android.rideway_app.R
import com.android.rideway_app.databinding.ActivityMypageBinding
import com.android.rideway_app.login.EntranceActivity

class MypageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMypageBinding
    lateinit var userProfile: SharedPreferences

    override fun onResume() {
        super.onResume()
        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        // resume 에서 프로필 이미지, 이메일, 닉네임, 주소를 세팅해줌
        if(userProfile.getBoolean("open",true)) binding.tvMyProfileOpen.text = "공개"
        else binding.tvMyProfileOpen.text = "비공개"
        binding.tvMyEmail.text = userProfile.getString("email",null)
        binding.tvMyNickName.text = userProfile.getString("nickname",null)
        binding.tvMyProfileSi.text = userProfile.getString("si",null)
        binding.tvMyProfileGun.text = userProfile.getString("gun",null)
        binding.ciProfileImage.setImageBitmap(ProfileSet.stringToBitmap(userProfile.getString("profileImage","null")))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 툴바의 제목과, 뒤로가기 버튼 설정
        binding.toolbarTitle.text = "프로필 설정"
        binding.ibBack.setOnClickListener {
            finish()
        }


        // 프로필 수정 누를 시 인텐트
        binding.btnProfileModify.setOnClickListener {
            val intent = Intent(this@MypageActivity, ProfileModifyActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
        }

        binding.btnEmailModify.setOnClickListener {
            val intent = Intent(this@MypageActivity, EmailModifyActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
        }

        // 비밀번호 변경 누를 시 인텐트
        binding.btnPasswordModify.setOnClickListener {
            val intent = Intent(this@MypageActivity, PasswordModifyActivity::class.java)
            startActivity(intent)
        }

        // 회원탈퇴 누를 시 인텐트
        binding.btnDeleteAccount.setOnClickListener {
            val intent = Intent(this@MypageActivity, DeleteAccountActivity::class.java)
            startActivity(intent)
        }

        // 로그아웃 누를 시 인텐트
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(this@MypageActivity)
                .setTitle("로그아웃")
                .setMessage("\n정말로 로그아웃 하시겠습니까?")
                .setPositiveButton("확인") { _, _ ->
                    userProfile.edit().clear().commit()
                    val intent = Intent(this@MypageActivity, EntranceActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                .setNegativeButton("취소"){ _, _ -> }
                .setCancelable(false)
                .create()
                .show()
        }
    }
}