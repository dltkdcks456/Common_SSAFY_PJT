package com.android.rideway_app.login

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.rideway_app.MainActivity
import com.android.rideway_app.R
import com.android.rideway_app.chat.Chat2Activity
import com.android.rideway_app.chat.ChatActivity
import com.android.rideway_app.chat.CreateChatRoomTestActivity
import com.android.rideway_app.community.WebViewActivity
import com.android.rideway_app.databinding.ActivityEntranceBinding
import com.android.rideway_app.map.MapActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.OverlayImage
import io.ticofab.androidgpxparser.parser.GPXParser
import io.ticofab.androidgpxparser.parser.domain.Gpx
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class EntranceActivity : AppCompatActivity() {
    lateinit var binding: ActivityEntranceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntranceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEntranceLogin.setOnClickListener {
            val intent = Intent(this@EntranceActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnEntranceRegister.setOnClickListener {
            val intent = Intent(this@EntranceActivity, FirstRegisterActivity::class.java)
            startActivity(intent)
            // 아래 코드는 인텐트 애니메이션을 바꿈
            overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
        }
//        var autoLogin: SharedPreferences = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
//        val id = autoLogin.getString("id",null)
//        val password = autoLogin.getString("password",null)
//        //로그인 정보가 있을경우 즉시 로그인
//        if(id != null && password != null){
//
//            LoginActivity().login(id,password)
//        }

        binding.tvIdSearch.setOnClickListener {

            // 원본
            val intent = Intent(this@EntranceActivity, IdSearchActivity::class.java)
            startActivity(intent)

        }

        binding.tvPassSearch.setOnClickListener {
            val intent = Intent(this@EntranceActivity, PassSearchActivity::class.java)
            startActivity(intent)
        }

    }
}