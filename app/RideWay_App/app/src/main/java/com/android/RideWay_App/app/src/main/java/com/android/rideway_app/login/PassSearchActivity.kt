package com.android.rideway_app.login

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.android.rideway_app.databinding.ActivityPassSearchBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class PassSearchActivity : AppCompatActivity() {
    lateinit var binding: ActivityPassSearchBinding

    private var nameCheck = false
    private var idCheck = false
    private var emailCheck = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이름 유효성 검사
        binding.etPasswordName.addTextChangedListener {
            nameCheck = false
            if(nameCheck(binding.etPasswordName.text.toString())) nameCheck = true
        }

        // 아이디 유효성 검사
        binding.etPasswordId.addTextChangedListener {
            idCheck = false
            if(isIdCorrect(binding.etPasswordId.text.toString())) idCheck = true
        }

        // 이메일 입력값 유효성 검사
        binding.etPasswordEmail.addTextChangedListener {
            emailCheck = false
            var email = binding.etPasswordEmail.text.toString()
            var pattern = android.util.Patterns.EMAIL_ADDRESS // 안드로이드에서 제공하는 이메일 양식 확인 패턴

            if(pattern.matcher(email).matches()) emailCheck = true
        }

        // 비밀번호 찾기 버튼 눌렀을 때
        binding.btnPassSearch.setOnClickListener {

            // 모든 입력 값이 유효하면 실행
            if(nameCheck && emailCheck && idCheck){
                passSearch(binding.etPasswordName.text.toString(), binding.etPasswordEmail.text.toString(), binding.etPasswordId.text.toString())
            }
            else{
                // 하나라도 유효하지 않다면 오류를 알려주는 다이얼로그를 띄워줌
                val dialog = AlertDialog.Builder(this@PassSearchActivity)
                dialog.create()
                dialog.setTitle("입력 오류 발생")
                dialog.setMessage("\n모든 기입 항목이 올바른지 확인해주십시오")
                dialog.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->  })
                dialog.show()
            }
        }
    }
    private fun nameCheck(name : String) : Boolean{
        val pwPattern = "^[가-힣]{2,4}$" // 영문, 숫자, 특수문자, 길이
        return Pattern.matches(pwPattern, name)
    }
    // 아이디가 영문 숫자로 5글자에서 20글자인지 확인하는 메소드
    private fun isIdCorrect(id: String): Boolean {
        val idPattern = "^(?=.*[a-z])[a-z[0-9]]{5,20}$" // 영문, 숫자 길이
        return Pattern.matches(idPattern, id)
    }

    private fun passSearch(name:String, email:String, id:String){
        val retrofitAPI = RetrofitClient.getInstance().create(LoginService::class.java)

        retrofitAPI.getNewPassword(PassSearchData(name,email,id)).enqueue(object :
            Callback<StringResultResponseData> {
            override fun onResponse(call: Call<StringResultResponseData>, response: Response<StringResultResponseData>) {
                if(response.isSuccessful){
                    println(response.body())
                    response.let{
                        //로그인 성공
                        if(it.code() == 200){
                            val intent = Intent(this@PassSearchActivity, PassSearchSuccessActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{//정보가 틀릴 때
                            val dialog = AlertDialog.Builder(this@PassSearchActivity)
                            dialog.create()
                            dialog.setTitle("계정 오류")
                            dialog.setMessage("\n입력된 정보의 계정이 없습니다.")
                            dialog.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> })
                            dialog.show()
                        }
                    }
                }
                else{
                    val dialog = AlertDialog.Builder(this@PassSearchActivity)
                    dialog.create()
                    dialog.setTitle("계정 오류")
                    dialog.setMessage("\n입력된 정보의 계정이 없습니다.")
                    dialog.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> })
                    dialog.show()
                }
            }

            override fun onFailure(call: Call<StringResultResponseData>, t: Throwable) {
                Toast.makeText(this@PassSearchActivity, "네트워크 접속 오류가 발생하였습니다.\n잠시후 다시 시도해주세요", Toast.LENGTH_LONG).show()
            }
        })
    }

}