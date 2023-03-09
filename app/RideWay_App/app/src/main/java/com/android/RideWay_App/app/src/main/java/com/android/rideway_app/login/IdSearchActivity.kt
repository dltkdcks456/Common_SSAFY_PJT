package com.android.rideway_app.login

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.android.rideway_app.databinding.ActivityIdSearchBinding
import com.android.rideway_app.databinding.ActivityPassSearchBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class IdSearchActivity : AppCompatActivity() {
    lateinit var binding:ActivityIdSearchBinding
    private var nameCheck = false
    private var emailCheck = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etIdName.addTextChangedListener {
            nameCheck = false
            if(nameCheck(binding.etIdName.text.toString())) nameCheck = true
        }


        binding.etIdEmail.addTextChangedListener {
            emailCheck = false
            var email = binding.etIdEmail.text.toString()
            var pattern = android.util.Patterns.EMAIL_ADDRESS // 안드로이드에서 제공하는 이메일 양식 확인 패턴

            if(pattern.matcher(email).matches()) emailCheck = true
        }

        // 아이디 찾기 버튼 눌렀을 때
        binding.btnIdSearch.setOnClickListener {

            // 모든 입력값이 올바를 때만 실행
            if(nameCheck && emailCheck ){
                idSearch(binding.etIdName.text.toString(), binding.etIdEmail.text.toString())
            }
            else{
                // 아니라면 다이얼로그로 오류를 알려줌
                val dialog = AlertDialog.Builder(this@IdSearchActivity)
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

    private fun idSearch(name : String, email : String){
        val retrofitAPI = RetrofitClient.getInstance().create(LoginService::class.java)

        retrofitAPI.getId(IdSearchData(name,email)).enqueue(object :
            Callback<IdSearchDataResponse> {
            override fun onResponse(call: Call<IdSearchDataResponse>, response: Response<IdSearchDataResponse>) {
                if(response.isSuccessful){
                    response.let{
                        //로그인 성공
                        if(it.code() == 200 && it.body()?.id != null){
                            val intent = Intent(this@IdSearchActivity, IdSearchSuccessActivity::class.java)
                            intent.putExtra("result", it.body()!!.id)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            val dialog = AlertDialog.Builder(this@IdSearchActivity)
                            dialog.create()
                            dialog.setTitle("계정 오류")
                            dialog.setMessage("\n입력된 정보의 계정이 없습니다.")
                            dialog.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> })
                            dialog.show()
                        }
                    }
                }
                else{
                    val dialog = AlertDialog.Builder(this@IdSearchActivity)
                    dialog.create()
                    dialog.setTitle("계정 오류")
                    dialog.setMessage("\n입력된 정보의 계정이 없습니다.")
                    dialog.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which -> })
                    dialog.show()
                }
            }

            override fun onFailure(call: Call<IdSearchDataResponse>, t: Throwable) {
                Toast.makeText(this@IdSearchActivity, "네트워크 접속 오류가 발생하였습니다.\n잠시후 다시 시도해주세요", Toast.LENGTH_LONG).show()
            }
        })
    }
}