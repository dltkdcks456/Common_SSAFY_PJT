package com.android.rideway_app.Mypage

import android.app.Activity
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.android.rideway_app.R
import com.android.rideway_app.databinding.ActivityEmailModifyBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.login.EmailModifyData
import com.android.rideway_app.retrofit.login.LoginService
import com.android.rideway_app.retrofit.login.StringResultResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.timer

class EmailModifyActivity : AppCompatActivity() {

    lateinit var binding : ActivityEmailModifyBinding
    lateinit var userProfile: SharedPreferences

    var emailCheck = false

    // 타이머를 위한 변수들
    var timer : Timer? = null
    var time = 0
    var isRunning = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        binding.ibBack.setOnClickListener {
            AlertDialog.Builder(this@EmailModifyActivity)
                .setTitle("수정 취소")
                .setMessage("\n수정을 그만두시겠습니까?")
                .setPositiveButton("네") { _, _ -> finish()}
                .setNegativeButton("아니오"){_,_ -> }
                .setCancelable(false)
                .create()
                .show()
        }

        // 이메일 입력에서 유효성 검사
        binding.etRegisterEmail.addTextChangedListener {
            // 이메일 관련 정보 초기화
            emailCheck = false
            binding.etRegisterEmail.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.littleRed))
            binding.btnModifyCheckEmail.isEnabled = false
            emailLayoutDisable()

            var email = binding.etRegisterEmail.text.toString()
            var pattern = android.util.Patterns.EMAIL_ADDRESS // 안드로이드에서 제공하는 이메일 양식 확인 패턴

            if(pattern.matcher(email).matches()){
                binding.etRegisterEmail.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.blue))
                binding.btnModifyCheckEmail.isEnabled = true
            }
        }

        // 이메일 인증 버튼 누르면 인증 칸이 따로 나오게 해줌
        binding.btnModifyCheckEmail.setOnClickListener {
            // 서버에 이메일 인증 번호 요청함
            emailCheck(binding.etRegisterEmail.text.toString())
        }

        // 이메일 인증코드를 정확히 입력했는지 유효성 검사
        binding.etRegisterEmailCheck.addTextChangedListener {
            binding.btnRegisterCheckEmailCode.isEnabled = false
            if(binding.etRegisterEmailCheck.text.toString().length==6){
                binding.btnRegisterCheckEmailCode.isEnabled = true
            }
        }

        // 이메일 인증 버튼 작동
        binding.btnRegisterCheckEmailCode.setOnClickListener {
            // 입력한 값이랑 서버에 등록된 값이랑 비교해서 맞으면 emailCheck를 true로 바꿈
            emailCodeCheck(binding.etRegisterEmailCheck.text.toString())
        }


        binding.btnModifyEmail.setOnClickListener {
            modifyMyEmail()
        }

    }

    private fun emailLayoutEnable(){
        binding.llEmailCheck.visibility = View.VISIBLE
        time = 180
        isRunning = true


        timer = timer(period = 1000){
            time--;
            val minute = time/60
            val second = time %60

            if(time<0) isRunning = false

            runOnUiThread {
                if(isRunning){
                    binding.tvMinuteTimer.text = "${minute}:"
                    binding.tvSecondTimer.text = if(second<10) "0${second}" else "${second}"
                }
                else{
                    emailLayoutDisable()
                }
            }
        }

    }

    // 이메일 인증 레이아웃 끄는 메소드
    private fun emailLayoutDisable(){
        timer?.cancel()
        binding.etRegisterEmailCheck.text.clear()
        binding.btnRegisterCheckEmailCode.isEnabled = false
        binding.llEmailCheck.visibility = View.GONE
    }

    // 이메일 인증 레이아웃 켜는 메소드
    private fun emailCheck(email : String){
        val retrofitAPI = RetrofitClient.getSimpleInstance().create(LoginService::class.java)

        retrofitAPI.getEmail(email).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        if(response.body()!!) emailLayoutEnable()
                        else Toast.makeText(this@EmailModifyActivity,"네트워크 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    // 이 부분에 들어오면 서버와 통신은 했는데 response가 성공적이지 못할 때
                    Toast.makeText(this@EmailModifyActivity,"이미 등록된 이메일 입니다.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@EmailModifyActivity,"네트워크 접속 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // code 값을 확인해주는 메소드
    private fun emailCodeCheck(code : String){
        val retrofitAPI = RetrofitClient.getSimpleInstance().create(LoginService::class.java)

        retrofitAPI.getCertCode(code).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        if(response.body()!!){
                            // 인증이 완료되었다면 이메일 수정 불가, 뒤에 색도 바꿔주고 버튼도 클릭 못하게 만들어서 실수로 바꾸지 못하게 만듦
                            binding.etRegisterEmail.isFocusable = false
                            binding.etRegisterEmail.setBackgroundColor(ContextCompat.getColor(applicationContext!!, R.color.background_blue))
                            binding.btnModifyCheckEmail.isEnabled = false
                            emailCheck = true
                            binding.btnModifyCheckEmail.visibility = View.GONE
                            binding.btnModifyEmail.visibility = View.VISIBLE
                            emailLayoutDisable()
                        }
                        else Toast.makeText(this@EmailModifyActivity,"잘못된 인증 값입니다.", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@EmailModifyActivity,"잘못된 인증 값입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this@EmailModifyActivity,"잘못된 인증 값입니다.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@EmailModifyActivity,"네트워크 접속 오류 발생", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 이메일 수정 메소드
    private fun modifyMyEmail(){
        val retrofitAPI = RetrofitClient.getInstance().create(LoginService::class.java)

        retrofitAPI.modifyMyEmail(EmailModifyData(userProfile.getString("token",null)!!, binding.etRegisterEmail.text.toString())).enqueue(object : Callback<StringResultResponseData> {
            override fun onResponse(call: Call<StringResultResponseData>, response: Response<StringResultResponseData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        if(response.body()!!.result == "중복된 이메일입니다."){
                            AlertDialog.Builder(this@EmailModifyActivity)
                                .setTitle("오류 발생")
                                .setMessage("\n네트워크에 오류가 발생하였습니다.\n다른 이메일로 시도해주시기 바랍니다.")
                                .setPositiveButton("확인") { _, _ -> }
                                .create()
                                .show()
                        }
                        else {
                            AlertDialog.Builder(this@EmailModifyActivity)
                                .setTitle("수정 완료!")
                                .setMessage("\n이메일이 성공적으로 수정되었습니다.")
                                .setPositiveButton("확인") { _, _ -> finish()}
                                .setCancelable(false)
                                .create()
                                .show()

                        }
                    }
                }
                else{
                    // 이 부분에 들어오면 서버와 통신은 했는데 response가 성공적이지 못할 때
                    Toast.makeText(this@EmailModifyActivity,"이미 등록된 이메일 입니다.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<StringResultResponseData>, t: Throwable) {
                Toast.makeText(this@EmailModifyActivity,"네트워크 접속 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder(this@EmailModifyActivity)
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