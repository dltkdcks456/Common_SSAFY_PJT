package com.android.rideway_app.Mypage

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.android.rideway_app.databinding.ActivityDeleteAccountBinding
import com.android.rideway_app.login.EntranceActivity
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.login.DeleteAccountData
import com.android.rideway_app.retrofit.login.LoginService
import com.android.rideway_app.retrofit.login.PassModifyData
import com.android.rideway_app.retrofit.login.StringResultResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteAccountActivity : AppCompatActivity() {

    lateinit var binding: ActivityDeleteAccountBinding
    lateinit var userProfile: SharedPreferences
    var passCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        binding.etCurrentPass.addTextChangedListener {
            passCheck = false
            if(binding.etCurrentPass.text.toString() == userProfile.getString("password",null)) passCheck = true
        }

        binding.ibBack.setOnClickListener {
            AlertDialog.Builder(this@DeleteAccountActivity)
                .setTitle("수정 취소")
                .setMessage("\n수정을 그만두시겠습니까?")
                .setPositiveButton("네") { _, _ -> finish()}
                .setNegativeButton("아니오"){_,_ -> }
                .setCancelable(false)
                .create()
                .show()
        }

        binding.btnDeleteAccount.setOnClickListener {
            if(passCheck){
                AlertDialog.Builder(this@DeleteAccountActivity)
                    .setTitle("로그아웃")
                    .setMessage("\n정말로 탈퇴 하시겠습니까?")
                    .setPositiveButton("확인") { _, _ -> deleteAccount() }
                    .setNegativeButton("취소"){ _, _ -> }
                    .setCancelable(false)
                    .create()
                    .show()
            }
            else {
                AlertDialog.Builder(this@DeleteAccountActivity)
                    .setTitle("입력 오류")
                    .setMessage("\n비밀번호가 일치하지 않습니다.")
                    .setPositiveButton("확인") { _, _ -> }
                    .create()
                    .show()
            }
        }

    }

    private fun deleteAccount(){
        val retrofitAPI = RetrofitClient.getInstance().create(LoginService::class.java)

        retrofitAPI.deleteAccount(DeleteAccountData(userProfile.getString("token",null)!!,binding.etCurrentPass.text.toString())).enqueue(object : Callback<StringResultResponseData> {
            override fun onResponse(call: Call<StringResultResponseData>, response: Response<StringResultResponseData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        if(response.body()!!.result == "탈퇴 성공"){
                            userProfile.edit().clear().commit()
                            val intent = Intent(this@DeleteAccountActivity, DeleteSuccessActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                        else {
                            AlertDialog.Builder(this@DeleteAccountActivity)
                                .setTitle("오류 발생")
                                .setMessage("\n네트워크 오류가 발생하였습니다.\n잠시후 다시 시도해주세요")
                                .setPositiveButton("확인") { _, _ -> }
                                .setCancelable(false)
                                .create()
                                .show()
                        }
                    }
                    else{
                        Toast.makeText(this@DeleteAccountActivity,"네트워크 접속 오류 발생" ,Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this@DeleteAccountActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<StringResultResponseData>, t: Throwable) {
                Toast.makeText(this@DeleteAccountActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder(this@DeleteAccountActivity)
                .setTitle("수정 취소")
                .setMessage("\n수정을 그만두시겠습니까?")
                .setPositiveButton("네") { _, _ -> finish()}
                .setNegativeButton("아니오"){_,_ -> }
                .setCancelable(false)
                .create()
                .show()
            return true
        }

        return false
    }

}