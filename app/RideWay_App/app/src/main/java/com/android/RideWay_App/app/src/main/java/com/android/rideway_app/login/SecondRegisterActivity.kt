package com.android.rideway_app.login

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.android.rideway_app.R
import com.android.rideway_app.databinding.ActivitySecondRegisterBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.login.LoginService
import com.android.rideway_app.retrofit.login.StringResultResponseData
import com.android.rideway_app.retrofit.login.SignUpData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SecondRegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondRegisterBinding
    // 아래 변수들은 각각 이메일 유효성 및 인증 여부, 아이디 유효성 및 중복체크 여부, 비밀번호 유효성 및 중복 여부, 닉네임 중복여부를 확인함
    // 모두가 true일 경우에만 회원가입 요청을 보낸다.
    var idCheck = false
    var passCheck = false
    var nicknameCheck = false
    var weightCheck = true
    var bikeWeightCheck = true
    var weight : String = "65"
    var bikeWeight : String = "12"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarTitle.text = "회원가입"

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
                    binding.tvRegisterIdCheck.text = "아이디는 영문자 및 숫자로 5자 이상이어야 합니다."
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
            if(binding.etRegisterNickName.text.isEmpty()) binding.tvRegisterNickNameCheck.text = "닉네임은 한글로 2~14자로 입력해주세요"
            else {
                // 항목이 비어있지 않으면 유효성 검사 후 옳다면 중복체크를 해줌
                if(isNickNameCorrect(binding.etRegisterNickName.text.toString())) nickNameCheck(binding.etRegisterNickName.text.toString())
            }
        }

        // 몸무게 변화 체크
        binding.etRegisterWeight.addTextChangedListener {
            if(binding.etRegisterWeight.text.isEmpty()){
                weightCheck = true
                weight = "65"
                binding.tvRegisterWeight.text = "미입력시 65kg으로 입력됩니다."
            }
            else{
                // 입력 값이 30이상일 때만 true
                weightCheck = binding.etRegisterWeight.text.toString().toInt() >= 30
                if(weightCheck) weight = binding.etRegisterWeight.text.toString()
                else binding.tvRegisterWeight.text = "최소 30이상 입력해주세요"
            }
        }

        // 자전거 무게 변화 체크
        binding.etRegisterBikeWeight.addTextChangedListener {
            if(binding.etRegisterBikeWeight.text.isEmpty()){
                bikeWeightCheck = true
                bikeWeight = "12"
                binding.tvRegisterBikeWeight.text = "미입력시 12kg으로 입력됩니다."
            }
            else{
                // 입력 값이 30이상일 때만 true
                bikeWeightCheck = binding.etRegisterBikeWeight.text.toString().toInt() >= 6
                if(bikeWeightCheck) bikeWeight = binding.etRegisterBikeWeight.text.toString()
                else binding.tvRegisterBikeWeight.text = "최소 6이상 입력해주세요"
            }
        }

        // 회원가입 버튼 누를 시
        binding.btnRegister.setOnClickListener {
           if(idCheck && passCheck && nicknameCheck && weightCheck && bikeWeightCheck){
               signUpRequest(binding.etRegisterId.text.toString(), binding.etRegisterPass.text.toString(), intent.getStringExtra("name")!!,
                   intent.getStringExtra("email")!!, binding.etRegisterNickName.text.toString(), "1", "true",
                   intent.getStringExtra("si")!!, intent.getStringExtra("gun")!!, intent.getStringExtra("dong")!!,weight, bikeWeight,
                   intent.getStringExtra("birth")!!, intent.getStringExtra("gender")!!)
           }
        }

    }

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

    // 닉네임 유효성 검사사
    private fun isNickNameCorrect(nickName: String): Boolean {
        val nickPattern = "^[가-힣]{2,14}$" // 닉네임 제공 방식 2글자~14글자 한글 여부
        return Pattern.matches(nickPattern, nickName)
    }

    // 아이디가 영문 숫자로 5글자에서 20글자인지 확인하는 메소드
    private fun isIdCorrect(id: String): Boolean {
        val idPattern = "^(?=.*[a-z])[a-z[0-9]]{5,20}$" // 영문, 숫자 길이
        return Pattern.matches(idPattern, id)
    }

    // 아이디 중복검사를 수행하기 위해 백엔드에서 boolean값을 가져오는 메소드
    private fun idCheck(id : String){
        val retrofitAPI = RetrofitClient.getSimpleInstance().create(LoginService::class.java)

        retrofitAPI.getIdCheck(id).enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        idCheckChange(response.body().toString().toBoolean()) // 만약 제대로 가져오면 수행되는 메소드
                    }
                    else{
                        Toast.makeText(this@SecondRegisterActivity,"결과는 받아왔지만 null값 가져옴", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this@SecondRegisterActivity,"결과 받아오기 실패", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@SecondRegisterActivity,"네트워크 접속 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // idcheck를 중복 여부에 맞는 값으로 변환해주는 메소드
    fun idCheckChange(check:Boolean){
        idCheck = !check

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
                        Toast.makeText(this@SecondRegisterActivity,"네트워크 접속 오류 발생" ,Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this@SecondRegisterActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@SecondRegisterActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("프로필 이미지를 바꾸기 위해서는 갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }

    /*
    *
    * val id : String,
    val password : String,
    val name : String,
    val email : String,
    val nickname : String,
    val permission : String,
    val image_path : String,
    val open : Boolean,
    val si : String,
    val gun : String,
    val dong : String,
    val weight : String,
    val age : String,
    val gender : String,
    * */

    private fun signUpRequest(id:String, password: String, name:String, email:String, nickname:String,
                              permission : String, open : String, si : String,
                              gun : String, dong : String, weight : String, cycle_weight : String, age: String, gender:String
                              ){
        val retrofitAPI = RetrofitClient.getInstance().create(LoginService::class.java)

        retrofitAPI.signupRequest(SignUpData(id, password, name, email,nickname, "1", cycle_weight, "true", si, gun,dong, weight, age, gender)).enqueue(object :
            Callback<StringResultResponseData> {
            override fun onResponse(call: Call<StringResultResponseData>, response: Response<StringResultResponseData>) {
                if(response.isSuccessful){
                    println(response.body())
                    response.let{
                        //로그인 성공
                        if(it.code() == 200){
                            val dialog = AlertDialog.Builder(this@SecondRegisterActivity)
                            dialog.create()
                            dialog.setTitle("축하합니다!")
                            dialog.setMessage("\n회원가입이 완료되었습니다.\n로그인 페이지로 이동합니다.")
                            dialog.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                                val intent = Intent(this@SecondRegisterActivity, LoginActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            })
                            dialog.setCancelable(false)
                            dialog.show()

                        }else{//정보가 틀릴 때
                            val dialog = AlertDialog.Builder(this@SecondRegisterActivity)
                            dialog.create()
                            dialog.setTitle("계정 오류")
                            dialog.setMessage("\n입력된 정보의 계정이 없습니다.")
                            dialog.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> })
                            dialog.show()
                        }
                    }
                }
                else{
                    val dialog = AlertDialog.Builder(this@SecondRegisterActivity)
                    dialog.create()
                    dialog.setTitle("계정 오류")
                    dialog.setMessage("\n입력된 정보의 계정이 없습니다.")
                    dialog.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> })
                    dialog.show()
                }
            }

            override fun onFailure(call: Call<StringResultResponseData>, t: Throwable) {
                Toast.makeText(this@SecondRegisterActivity, "네트워크 접속 오류가 발생하였습니다.\n잠시후 다시 시도해주세요", Toast.LENGTH_LONG).show()
            }
        })
    }
}