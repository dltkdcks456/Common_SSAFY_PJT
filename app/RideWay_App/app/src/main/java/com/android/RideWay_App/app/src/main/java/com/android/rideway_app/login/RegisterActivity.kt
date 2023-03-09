package com.android.rideway_app.login

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.android.rideway_app.R
import com.android.rideway_app.databinding.ActivityRegisterBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.login.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern
import kotlin.concurrent.timer

class RegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegisterBinding
    // 아래 변수들은 각각 이메일 유효성 및 인증 여부, 아이디 유효성 및 중복체크 여부, 비밀번호 유효성 및 중복 여부, 닉네임 중복여부를 확인함
    // 모두가 true일 경우에만 회원가입 요청을 보낸다.
    var emailCheck = false
    var idCheck = false
    var passCheck = false
    var nicknameCheck = false
    var nameCheck = false

    // 타이머를 위한 변수들
    var timer : Timer? = null
    var time = 0
    var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이메일 입력에서 유효성 검사
        binding.etRegisterEmail.addTextChangedListener {
            // 이메일 관련 정보 초기화
            emailCheck = false
            binding.etRegisterEmail.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.littleRed))
            binding.btnRegisterCheckEmail.isEnabled = false
            emailLayoutDisable()

            var email = binding.etRegisterEmail.text.toString()
            var pattern = android.util.Patterns.EMAIL_ADDRESS // 안드로이드에서 제공하는 이메일 양식 확인 패턴

            if(pattern.matcher(email).matches()){
                binding.etRegisterEmail.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.blue))
                binding.btnRegisterCheckEmail.isEnabled = true
            }
        }

        binding.etRegisterEmailCheck.addTextChangedListener {
            binding.btnRegisterCheckEmailCode.isEnabled = false
            if(binding.etRegisterEmailCheck.text.toString().length==6){
                binding.btnRegisterCheckEmailCode.isEnabled = true
            }
        }

        // 이메일 확인 버튼 리스너 추가
        binding.btnRegisterCheckEmailCode.setOnClickListener {
            // 입력한 값이랑 서버에 등록된 값이랑 비교해서 맞으면 emailCheck를 true로 바꿈
            emailCodeCheck(binding.etRegisterEmailCheck.text.toString())
        }

        // 이메일 인증 버튼 누르면 인증 칸이 따로 나오게 해줌
        binding.btnRegisterCheckEmail.setOnClickListener {
            // 서버에 이메일 인증 번호 요청함
            emailSend(binding.etRegisterEmail.text.toString())
            // 그 다음 이메일 인증 확인 layout을 활성화 함
            emailLayoutEnable()

        }


        // id 유효성 확인
        binding.etRegisterId.addTextChangedListener {
            idCheck = false
            binding.tvRegisterIdCheck.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.littleRed))

            if(binding.etRegisterId.text.isEmpty()) {
                binding.tvRegisterIdCheck.text = ""
                binding.btnRegisterCheckId.isEnabled = false
            }
            else{
                if( !isIdCorrect(binding.etRegisterId.text.toString())){
                    binding.tvRegisterIdCheck.text = "아이디는 영문자 및 숫자로 6자 이상이어야 합니다."
                    binding.btnRegisterCheckId.isEnabled = false
                }
                else{
                    binding.tvRegisterIdCheck.text = "중복확인을 눌러주세요."
                    binding.btnRegisterCheckId.isEnabled = true
                }
            }
        }

        // 아이디 중복 확인
        binding.btnRegisterCheckId.setOnClickListener {
            idCheck(binding.etRegisterId.text.toString())
        }

        // 비밀번호 입력
        binding.etRegisterPass.addTextChangedListener {
            passCheck(binding.etRegisterPass.text.toString(), binding.etRegisterPassCheck.text.toString())
            if (isPassCorrect(binding.etRegisterPass.text.toString())){
                binding.tvRegisterPass.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.blue))
                binding.tvRegisterPass.text = "사용 가능한 비밀번호입니다."
            }
            else{
                binding.tvRegisterPass.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.littleRed))
                binding.tvRegisterPass.text = "영문, 숫자, 특수문자로 8-20자로 구성해주세요"

            }
        }

        // 비밀번호 확인
        binding.etRegisterPassCheck.addTextChangedListener {
            passCheck(binding.etRegisterPass.text.toString(), binding.etRegisterPassCheck.text.toString())
        }

        // 닉네임 확인
        binding.etRegisterNickName.addTextChangedListener {
            nicknameCheck = false
            binding.tvRegisterNickNameCheck.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.littleRed))
            if(binding.etRegisterNickName.text.isEmpty()) binding.tvRegisterNickNameCheck.text = ""
            else nickNameCheck(binding.etRegisterNickName.text.toString())

        }

        // 시 군 구


        // 회원가입 확인
        binding.btnRegister.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            if(emailCheck && idCheck && passCheck && nicknameCheck){
                // 여기서 전송을 보내보고 문제가 없다면
                builder.setTitle("회원가입이 완료되었습니다.")
                    .setMessage("\n")
                    .setPositiveButton("확인",DialogInterface.OnClickListener { dialog, id ->
                        finish()
                    })
                builder.show()
            }
            else{
                builder.setTitle("입력 사항을 모두 입력해주세요.")
                    .setMessage("\n")
                    .setPositiveButton("확인",DialogInterface.OnClickListener { dialog, id ->
                    })
                builder.show()
            }

        }


    }

    // 비밀번호가 비었는지 입력이 잘 되었는지 확인하는 메소드
    private fun passCheck(password1: String, password2: String){

        if(password2.isEmpty()) {
            binding.tvRegisterPassCheck.text = ""
            passCheck = false
            return
        }

        passCheck = password1 == password2

        if (passCheck){
            binding.tvRegisterPassCheck.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.blue))
            binding.tvRegisterPassCheck.text = "비밀번호가 일치합니다."
        }
        else{
            binding.tvRegisterPassCheck.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.littleRed))
            binding.tvRegisterPassCheck.text = "비밀번호가 일치하지 않습니다."
        }
    }

    // 비밀번호가 영어 숫자 특수문자 길이가 맞는지 확인하는 메소드
    private fun isPassCorrect(password: String): Boolean {
        val pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,20}$" // 영문, 숫자, 특수문자, 길이
        return Pattern.matches(pwPattern, password)
    }

    // 아이디가 영문 숫자로 5글자에서 20글자인지 확인하는 메소드
    private fun isIdCorrect(id: String): Boolean {
        val idPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[a-z[0-9]]{5,20}$" // 영문, 숫자 길이
        return Pattern.matches(idPattern, id)
    }

    // 아이디 중복검사를 수행하기 위해 백엔드에서 boolean값을 가져오는 메소드
    private fun idCheck(id : String){
        val retrofitAPI = RetrofitClient.getSimpleInstance().create(LoginService::class.java)

        retrofitAPI.getIdCheck(id).enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        idCheckChange(response.body().toString().toBoolean()) // 만약 제대로 가져오면 수행되는 메소드
                    }
                    else{
                        Toast.makeText(this@RegisterActivity,"결과는 받아왔지만 null값 가져옴",Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this@RegisterActivity,"결과 받아오기 실패",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@RegisterActivity,"네트워크 접속 실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    // idcheck를 중복 여부에 맞는 값으로 변환해주는 메소드
    fun idCheckChange(check:Boolean){
        idCheck = check

        if (!check){
            binding.tvRegisterIdCheck.text = "사용가능한 아이디 입니다."
            binding.tvRegisterIdCheck.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.blue))
            binding.btnRegisterCheckId.isEnabled = true
        }
        else{
            binding.tvRegisterIdCheck.text = "이미 사용중인 아이디 입니다."
            binding.tvRegisterIdCheck.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.littleRed))
            binding.btnRegisterCheckId.isEnabled = true
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
    private fun emailLayoutDisable(){
        timer?.cancel()
        binding.etRegisterEmailCheck.text.clear()
        binding.btnRegisterCheckEmailCode.isEnabled = false
        binding.llEmailCheck.visibility = View.GONE
    }

    // 이메일을 인증 코드를 전송하는 메소드
    private fun emailSend(email : String){
        val retrofitAPI = RetrofitClient.getSimpleInstance().create(LoginService::class.java)

        retrofitAPI.getEmail(email).enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        if(response.body()!!) emailLayoutEnable()
                        else Toast.makeText(this@RegisterActivity,"네트워크 오류가 발생하였습니다.",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        // 이 부분은 response를 받는데는 성공했지만 그게 비어있을 때
                        Toast.makeText(this@RegisterActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    // 이 부분에 들어오면 서버와 통신은 했는데 response가 성공적이지 못할 때
                    Toast.makeText(this@RegisterActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@RegisterActivity,"네트워크 접속 실패",Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 이메일 인증코드를 확인해보는 코드
    private fun emailCodeCheck(code : String){
        val retrofitAPI = RetrofitClient.getSimpleInstance().create(LoginService::class.java)

        retrofitAPI.getCertCode(code).enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        if(response.body()!!){
                            // 인증이 완료되었다면 이메일 수정 불가, 뒤에 색도 바꿔주고 버튼도 클릭 못하게 만들어서 실수로 바꾸지 못하게 만듦
                            binding.etRegisterEmail.isFocusable = false
                            binding.etRegisterEmail.setBackgroundColor(ContextCompat.getColor(applicationContext!!, R.color.background_blue))
                            binding.btnRegisterCheckEmail.isEnabled = false
                            emailCheck = true
                            emailLayoutDisable()
                        }
                        else Toast.makeText(this@RegisterActivity,"네트워크 오류가 발생하였습니다.",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@RegisterActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this@RegisterActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@RegisterActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun nickNameCheck(nickName : String){
        val retrofitAPI = RetrofitClient.getSimpleInstance().create(LoginService::class.java)

        retrofitAPI.getNickNameCode(nickName).enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        if(!response.body()!!){
                            binding.tvRegisterNickNameCheck.text = "사용가능한 닉네임 입니다."
                            binding.tvRegisterNickNameCheck.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.blue))
                            nicknameCheck = true
                        }
                        else binding.tvRegisterNickNameCheck.text = "이미 사용중인 닉네임입니다."
                    }
                    else{
                        Toast.makeText(this@RegisterActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this@RegisterActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@RegisterActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
            }
        })
    }
}