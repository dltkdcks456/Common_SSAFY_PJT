package com.android.rideway_app

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.android.rideway_app.login.EntranceActivity
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    lateinit var userProfile : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 로그인 체크를 해주고 로그인되지 않는 경우 entranceActivity로 인텐트해준다.
        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        //현재 로그인 번호가 남아있는지 확인한다.

        Handler().postDelayed({
            loginCheck()
//            val intent = Intent(this, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//            startActivity(intent)
//            finish()
        },DURATION)

    }
    companion object {
        private const val DURATION : Long = 2000
    }

    private fun loginCheck(){
        println("로그인 정보 확인!!")
        val id = userProfile.getString("id",null)
        val password = userProfile.getString("password",null)
        println("로그인 정보 확인!!" +id + "  " + password)
        //로그인 정보가 있을경우 즉시 로그인
        if(id != null && password != null){
            login(id,password)
        }
        else{
            val intent = Intent(this@SplashActivity, EntranceActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
//    private fun login(response: Response<LoginDataResponse>){
//
//
//        val intent : Intent = Intent(this@LoginActivity, MainActivity::class.java)
//        intent.putExtra("response", response.body()!![0].title.toString())
//     //아래 코드는 새로 여는 intent 외의 모든 쌓여있는 activity stack을 다 비워주는 코드
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//        finish()
//    }

    fun login(id : String , password : String){
        val retrofitAPI = RetrofitClient.getInstance().create(LoginService::class.java)

        retrofitAPI.getLoginData(LoginData(id,password)).enqueue(object :
            Callback<LoginDataResponse> {
            override fun onResponse(call: Call<LoginDataResponse>, response: Response<LoginDataResponse>) {
                if(response.isSuccessful){
                    println(response.body())
                    response.let{
                        //로그인 성공
                        if(it.code() == 200){
                            //로그인 정보 저장
                            val autoLoginEdit = userProfile.edit()
                            autoLoginEdit.putString("id",id)
                            autoLoginEdit.putString("password",password)
                            autoLoginEdit.putInt("user_id",it.body()!!.user_id)
                            autoLoginEdit.putString("user_image",it.body()!!.image_path)
                            autoLoginEdit.putInt("permission",it.body()!!.permission)
                            autoLoginEdit.putString("token",it.body()!!.token)
                            autoLoginEdit.apply()

                            setUserProfile(it.body()!!.token)

                        }else{
                            val intent = Intent(this@SplashActivity, EntranceActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                else{
                    val intent = Intent(this@SplashActivity, EntranceActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<LoginDataResponse>, t: Throwable) {
                val intent = Intent(this@SplashActivity, EntranceActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    fun setUserProfile(token : String){
        val retrofitAPI = RetrofitClient.getInstance().create(LoginService::class.java)

        retrofitAPI.getMyProfile(TokenData(token)).enqueue(object : Callback<UserProfileData>{
            override fun onResponse(call: Call<UserProfileData>, response: Response<UserProfileData>) {
                if(response.isSuccessful){
                    println(response.body())
                    response.let{
                        val autoLoginEdit = userProfile.edit()
                        autoLoginEdit.putString("name",it.body()!!.name)
                        autoLoginEdit.putString("email",it.body()!!.email)
                        autoLoginEdit.putString("nickname",it.body()!!.nickname)
                        autoLoginEdit.putString("image_path",it.body()!!.image_path)
                        autoLoginEdit.putBoolean("open",it.body()!!.open)
                        autoLoginEdit.putString("si",it.body()!!.si)
                        autoLoginEdit.putString("gun",it.body()!!.gun)
                        autoLoginEdit.putString("dong",it.body()!!.dong)
                        autoLoginEdit.putInt("weight",it.body()!!.weight)
                        autoLoginEdit.putInt("cycle_weight",it.body()!!.cycle_weight)
                        autoLoginEdit.putString("age",it.body()!!.age)
                        autoLoginEdit.putString("gender",it.body()!!.gender)
                        autoLoginEdit.apply()

                        //로그인 완료 후 메인페이지 이동
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                else{
                    val intent = Intent(this@SplashActivity, EntranceActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<UserProfileData>, t: Throwable) {
                val intent = Intent(this@SplashActivity, EntranceActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }
}