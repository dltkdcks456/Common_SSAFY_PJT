package com.android.rideway_app.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.rideway_app.databinding.ActivityChat2Binding
import android.util.Log
import android.widget.Toast
import com.android.rideway_app.databinding.ActivityChatBinding
import com.google.gson.Gson
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger


class Chat2Activity : AppCompatActivity() {
    lateinit var binding: ActivityChat2Binding
    var gson = Gson()
    lateinit var stompConnection: Disposable
    lateinit var topic: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChat2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = "ws://10.0.2.2:8080/ws-stomp/websocket"
        val intervalMillis = 5000L
        val client = OkHttpClient.Builder()
//                .addInterceptor { it.proceed(it.request().newBuilder().header("Authorization", "bearer 68d20faa-54c4-11e8-8195-98ded0151692").build()) }
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

        val stomp = StompClient(client, intervalMillis).apply { this@apply.url = url }

        // connect
        stompConnection = stomp.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {

                    // subscribe
                    topic = stomp.join("/topic/chat/message").subscribe { Log.d("abcd", "결과는 이렇게 나온다 : " + it)
                        haha(it)
                    }


                }
                Event.Type.CLOSED -> {

                }
                Event.Type.ERROR -> {

                }
                else -> {}
            }
        }


        binding.send.setOnClickListener {
                stomp.send("/topic/chat/message", "가나다라마바사" ).subscribe {
                    if (it) {
                    }
                }
        }


        binding.disconnect.setOnClickListener {
            topic.dispose()
        }

    }

    private fun haha(abc : String){
        binding.hello.text = abc
    }
}