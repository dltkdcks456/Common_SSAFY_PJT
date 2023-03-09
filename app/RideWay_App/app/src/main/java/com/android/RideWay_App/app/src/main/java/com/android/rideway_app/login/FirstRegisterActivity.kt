package com.android.rideway_app.login

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.android.rideway_app.R
import com.android.rideway_app.databinding.ActivityFirstRegisterBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.login.LoginService
import com.android.rideway_app.retrofit.region.DongData
import com.android.rideway_app.retrofit.region.GunData
import com.android.rideway_app.retrofit.region.RegionService
import com.android.rideway_app.retrofit.region.SiData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.regex.Pattern
import kotlin.concurrent.timer

class FirstRegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityFirstRegisterBinding

    var emailCheck = false
    var nameCheck = false
    var birthCheck = false

    // 타이머를 위한 변수들
    var timer : Timer? = null
    var time = 0
    var isRunning = false

    lateinit var siList : SiData
    lateinit var gunList : GunData
    lateinit var dongList : DongData

    lateinit var si : String
    lateinit var gun : String
    lateinit var dong : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarTitle.text = "회원가입"

        getAllSi()

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

        // 이메일 인증 버튼 누르면 인증 칸이 따로 나오게 해줌
        binding.btnRegisterCheckEmail.setOnClickListener {
            // 서버에 이메일 인증 번호 요청함
            emailCheck(binding.etRegisterEmail.text.toString())
        }

        // 생년월일 입력 확인
        binding.etRegisterBirth.addTextChangedListener {
            birthCheck = true
        }

        // 생년월일 다이얼로그로 입력
        binding.birthPicker.setOnClickListener {

            val dateListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                binding.etRegisterBirth.setText("${year}-${month+1}-${dayOfMonth}")
            }

            val datePickerDialog = DatePickerDialog(this@FirstRegisterActivity,R.style.MySpinnerDatePickerStyle ,dateListener,2013,1,1)
            datePickerDialog.show()
        }

        binding.etRegisterName.addTextChangedListener {
            val name = binding.etRegisterName.text.toString()
            binding.etRegisterName.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.black))
            nameCheck = false

            if(nameCheck(name)){
                nameCheck = true
            }
            else{
                binding.etRegisterName.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.littleRed))
            }
        }

        // 다음 버튼
        binding.btnRegisterNext.setOnClickListener {
            if(emailCheck && nameCheck && birthCheck){
                val gender : String

                if(binding.rbRegisterMale.isChecked){
                    gender = "남"
               }
                else{
                    gender = "녀"
                }
                val intent = Intent(this@FirstRegisterActivity, SecondRegisterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                intent.putExtra("si",si)
                intent.putExtra("gun",gun)
                intent.putExtra("dong",dong)
                intent.putExtra("email",binding.etRegisterEmail.text.toString())
                intent.putExtra("name",binding.etRegisterName.text.toString())
                intent.putExtra("birth",binding.etRegisterBirth.text.toString())
                intent.putExtra("gender", gender)
                startActivity(intent)
            }
            else{
                AlertDialog.Builder(this)
                    .setTitle("입력 양식 부족")
                    .setMessage("\n비어있는 항목이 있는지 확인해주세요")
                    .setPositiveButton("닫기") { _, _ -> }
                    .create()
                    .show()
            }

        }
    }

    private fun nameCheck(name : String) : Boolean{
        val pwPattern = "^[가-힣]{2,4}$" // 영문, 숫자, 특수문자, 길이
        return Pattern.matches(pwPattern, name)
    }

    // 이메일 인증 레이아웃 켜주기
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
                        else Toast.makeText(this@FirstRegisterActivity,"네트워크 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    // 이 부분에 들어오면 서버와 통신은 했는데 response가 성공적이지 못할 때
                    Toast.makeText(this@FirstRegisterActivity,"이미 등록된 이메일 입니다.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@FirstRegisterActivity,"네트워크 접속 실패", Toast.LENGTH_SHORT).show()
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
                            binding.btnRegisterCheckEmail.isEnabled = false
                            emailCheck = true
                            emailLayoutDisable()
                        }
                        else Toast.makeText(this@FirstRegisterActivity,"잘못된 인증 값입니다.", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@FirstRegisterActivity,"네트워크 접속 오류 발생", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this@FirstRegisterActivity,"네트워크 접속 오류 발생", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@FirstRegisterActivity,"네트워크 접속 오류 발생", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 시 가져오기
    private fun getAllSi(){
        val retrofitAPI = RetrofitClient.getInstance().create(RegionService::class.java)

        retrofitAPI.getAllSi().enqueue(object : Callback<SiData> {
            override fun onResponse(call: Call<SiData>, response: Response<SiData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        siList = response.body()!!
                        val list : MutableList<String> = mutableListOf()
                        for( si in siList) list.add(si.name)
                        val adapter = ArrayAdapter<String>(this@FirstRegisterActivity, android.R.layout.simple_list_item_1, list)
                        binding.spSi.adapter = adapter

                        if(siList[0].name.isEmpty()){
                            AlertDialog.Builder(this@FirstRegisterActivity)
                                .setTitle("네트워크 접속 오류")
                                .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                                .setPositiveButton("확인") { _, _ -> finish()}
                                .setCancelable(false)
                                .create()
                                .show()
                        }
                        else{
                            binding.spSi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    getGun(siList[position].si_code.toString())
                                    si = siList[position].name
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }

                            }
                        }


                    }
                    else{
                        AlertDialog.Builder(this@FirstRegisterActivity)
                            .setTitle("네트워크 접속 오류")
                            .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                            .setPositiveButton("확인") { _, _ -> finish()}
                            .setCancelable(false)
                            .create()
                            .show()
                    }
                }
                else{
                    AlertDialog.Builder(this@FirstRegisterActivity)
                        .setTitle("네트워크 접속 오류")
                        .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                        .setPositiveButton("확인") { _, _ -> finish()}
                        .setCancelable(false)
                        .create()
                        .show()
                }
            }
            override fun onFailure(call: Call<SiData>, t: Throwable) {
                t.printStackTrace()
                AlertDialog.Builder(this@FirstRegisterActivity)
                    .setTitle("네트워크 접속 오류")
                    .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                    .setPositiveButton("확인") { _, _ -> finish()}
                    .setCancelable(false)
                    .create()
                    .show()
            }
        })
    }

    private fun getGun(si_code : String){
        val retrofitAPI = RetrofitClient.getInstance().create(RegionService::class.java)

        retrofitAPI.getSelectedGun(si_code).enqueue(object : Callback<GunData> {
            override fun onResponse(call: Call<GunData>, response: Response<GunData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        gunList = response.body()!!
                        val list2 : MutableList<String> = mutableListOf()
                        for( gun in gunList) list2.add(gun.name)
                        val adapter = ArrayAdapter<String>(this@FirstRegisterActivity, android.R.layout.simple_list_item_1, list2)
                        binding.spGun.adapter = adapter

                        binding.spGun.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                getDong(gunList[position].si_code.toString(), gunList[position].gun_code.toString())
                                gun = gunList[position].name
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }

                        }

                    }
                    else{
                        AlertDialog.Builder(this@FirstRegisterActivity)
                            .setTitle("네트워크 접속 오류")
                            .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                            .setPositiveButton("확인") { _, _ -> finish()}
                            .setCancelable(false)
                            .create()
                            .show()
                    }
                }
                else{
                    AlertDialog.Builder(this@FirstRegisterActivity)
                        .setTitle("네트워크 접속 오류")
                        .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                        .setPositiveButton("확인") { _, _ -> finish()}
                        .setCancelable(false)
                        .create()
                        .show()
                }
            }
            override fun onFailure(call: Call<GunData>, t: Throwable) {
                AlertDialog.Builder(this@FirstRegisterActivity)
                    .setTitle("네트워크 접속 오류")
                    .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                    .setPositiveButton("확인") { _, _ -> finish()}
                    .setCancelable(false)
                    .create()
                    .show()
            }
        })
    }

    private fun getDong(si_code : String, gun_code : String){
        val retrofitAPI = RetrofitClient.getInstance().create(RegionService::class.java)

        retrofitAPI.getSelectedDong(si_code, gun_code).enqueue(object : Callback<DongData> {
            override fun onResponse(call: Call<DongData>, response: Response<DongData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        dongList = response.body()!!
                        val list2 : MutableList<String> = mutableListOf()
                        for( dong in dongList) list2.add(dong.name)
                        val adapter = ArrayAdapter<String>(this@FirstRegisterActivity, android.R.layout.simple_list_item_1, list2)
                        binding.spDong.adapter = adapter

                        binding.spDong.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                dong = dongList[position].name
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }
                        }

                    }
                    else{
                        AlertDialog.Builder(this@FirstRegisterActivity)
                            .setTitle("네트워크 접속 오류")
                            .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                            .setPositiveButton("확인") { _, _ -> finish()}
                            .setCancelable(false)
                            .create()
                            .show()
                    }
                }
                else{
                    AlertDialog.Builder(this@FirstRegisterActivity)
                        .setTitle("네트워크 접속 오류")
                        .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                        .setPositiveButton("확인") { _, _ -> finish()}
                        .setCancelable(false)
                        .create()
                        .show()
                }
            }
            override fun onFailure(call: Call<DongData>, t: Throwable) {
                AlertDialog.Builder(this@FirstRegisterActivity)
                    .setTitle("네트워크 접속 오류")
                    .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                    .setPositiveButton("확인") { _, _ -> finish()}
                    .setCancelable(false)
                    .create()
                    .show()
            }
        })
    }
}