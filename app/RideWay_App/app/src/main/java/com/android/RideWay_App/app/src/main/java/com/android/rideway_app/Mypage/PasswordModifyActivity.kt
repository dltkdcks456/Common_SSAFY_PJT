package com.android.rideway_app.Mypage

import android.app.Activity
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.android.rideway_app.R
import com.android.rideway_app.databinding.ActivityPasswordModifyBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.login.LoginService
import com.android.rideway_app.retrofit.login.PassModifyData
import com.android.rideway_app.retrofit.login.StringResultResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class PasswordModifyActivity : AppCompatActivity() {
    lateinit var binding:ActivityPasswordModifyBinding
    lateinit var userProfile: SharedPreferences

    var currentPassword = false
    var newPassword = false
    var newPasswordCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        binding.ibBack.setOnClickListener {
            AlertDialog.Builder(this@PasswordModifyActivity)
                .setTitle("수정 취소")
                .setMessage("\n수정을 그만두시겠습니까?")
                .setPositiveButton("네") { _, _ -> finish()}
                .setNegativeButton("아니오"){_,_ -> }
                .setCancelable(false)
                .create()
                .show()
        }

        binding.etCurrentPass.addTextChangedListener {
            currentPassword = false
            if(!binding.etCurrentPass.text.isEmpty()){
                currentPassword = binding.etCurrentPass.text.toString()==userProfile.getString("password",null)
            }
        }

        binding.etNewPassword.addTextChangedListener {
            newPassword = false
            newPasswordCheck = false
            binding.tvModifyPassCheck.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.blue))
            if(binding.etNewPassword.text.isEmpty()){
                binding.tvModifyPassCheck.text = "영문, 숫자, 특수문자로 8-20자로 구성해주세요"
            }
            else{
                if(isPassCorrect(binding.etNewPassword.text.toString())){
                    newPassword = true
                    binding.tvModifyPassCheck.text = "사용 가능한 비밀번호입니다."
                    if(!binding.etNewPasswordCheck.text.isEmpty()){
                        passCompare(binding.etNewPassword.text.toString(), binding.etNewPasswordCheck.text.toString())
                    }
                }
                else{
                    binding.tvModifyPassCheck.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.littleRed))
                    binding.tvModifyPassCheck.text = "영문, 숫자, 특수문자로 8-20자로 구성해주세요"
                }
            }
        }

        binding.etNewPasswordCheck.addTextChangedListener {
            newPasswordCheck = false
            binding.tvModifyPassConfirm.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.blue))
            if(binding.etNewPassword.text.isEmpty()){
                newPasswordCheck = false
                binding.tvModifyPassConfirm.text = ""
            }
            else{
                if(binding.etNewPassword.text.toString() == binding.etNewPasswordCheck.text.toString()){
                    newPasswordCheck = true
                    binding.tvModifyPassConfirm.text = "비밀번호가 일치합니다."
                }
                else{
                    binding.tvModifyPassConfirm.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.littleRed))
                    binding.tvModifyPassConfirm.text = "비밀번호가 일치하지 않습니다."
                }
                passCompare(binding.etNewPassword.text.toString(), binding.etNewPasswordCheck.text.toString())
            }
        }

        binding.tvComplete.setOnClickListener {
            if(currentPassword && newPassword && newPasswordCheck){
                if(binding.etNewPasswordCheck.text.toString() == userProfile.getString("password",null)){
                    AlertDialog.Builder(this)
                        .setTitle("비밀번호 일치")
                        .setMessage("\n비밀번호가 현재 비밀번호와 일치합니다.\n새로운 비밀번호를 입력해주세요.")
                        .setPositiveButton("확인") { _, _ -> }
                        .setCancelable(false)
                        .create()
                        .show()
                }
                else{
                    passChange()
                }
            }else{
                if(!currentPassword){
                    AlertDialog.Builder(this)
                        .setTitle("입력 문제")
                        .setMessage("\n현재 비밀번호를 재입력해주세요")
                        .setPositiveButton("확인") { _, _ -> }
                        .setCancelable(false)
                        .create()
                        .show()
                }
                else if(!newPassword || !newPasswordCheck){
                    AlertDialog.Builder(this)
                        .setTitle("입력 문제")
                        .setMessage("\n변경할 비밀번호를 확인해주세요")
                        .setPositiveButton("확인") { _, _ -> }
                        .setCancelable(false)
                        .create()
                        .show()
                }

            }

        }

    }

    private fun passCompare(pass1 : String, pass2 : String){
        if(pass1==pass2){
            binding.tvModifyPassConfirm.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.blue))
            binding.tvModifyPassConfirm.text = "비밀번호가 일치합니다."
            newPasswordCheck = true
        }
        else {
            binding.tvModifyPassConfirm.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.littleRed))
            binding.tvModifyPassConfirm.text = "비밀번호가 일치하지 않습니다."
            newPasswordCheck = false
        }
    }

    private fun isPassCorrect(password: String): Boolean {
        val pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,20}$" // 영문, 숫자, 특수문자, 길이
        return Pattern.matches(pwPattern, password)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder(this@PasswordModifyActivity)
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

    private fun passChange(){
        val retrofitAPI = RetrofitClient.getInstance().create(LoginService::class.java)

        retrofitAPI.modifyMyPassword(PassModifyData(binding.etCurrentPass.text.toString(), binding.etNewPasswordCheck.text.toString(),
        userProfile.getString("token",null)!!)).enqueue(object : Callback<StringResultResponseData> {
            override fun onResponse(call: Call<StringResultResponseData>, response: Response<StringResultResponseData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        if(response.body()!!.result == "설정이 변경되었습니다."){

                            userProfile.edit().putString("password",binding.etNewPasswordCheck.text.toString())

                            AlertDialog.Builder(this@PasswordModifyActivity)
                                .setTitle("수정 완료!")
                                .setMessage("\n비밀번호가 수정되었습니다.")
                                .setPositiveButton("확인") { _, _ -> finish()}
                                .setCancelable(false)
                                .create()
                                .show()
                        }
                        else {
                            AlertDialog.Builder(this@PasswordModifyActivity)
                                .setTitle("오류 발생")
                                .setMessage("\n네트워크 오류가 발생하였습니다.\n잠시후 다시 시도해주세요")
                                .setPositiveButton("확인") { _, _ -> }
                                .setCancelable(false)
                                .create()
                                .show()
                        }
                    }
                    else{
                        Toast.makeText(this@PasswordModifyActivity,"네트워크 접속 오류 발생" ,Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this@PasswordModifyActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<StringResultResponseData>, t: Throwable) {
                Toast.makeText(this@PasswordModifyActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
            }
        })
    }

}