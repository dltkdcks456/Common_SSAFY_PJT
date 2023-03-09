package com.android.rideway_app

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.rideway_app.databinding.FragmentRunningBinding
import com.android.rideway_app.map.MapActivity
import java.text.SimpleDateFormat
import java.util.*


class RunningFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var container : ViewGroup
    lateinit var binding: FragmentRunningBinding
    private lateinit var userProfile: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        userProfile = container.context.getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        val mNow = System.currentTimeMillis();
        val mDate = Date(mNow);
        val dateFormat2 = SimpleDateFormat("dd")
        val getTime: String = dateFormat2.format(mDate)

        if(userProfile.getString("date", "32") != getTime){
            userProfile.edit().putString("date", getTime).apply()
            userProfile.edit().putInt("todayTime",0).apply()
            userProfile.edit().putInt("todayDist",0).apply()
            userProfile.edit().putInt("todayCal",0).apply()
        }


        binding.tvTodayDist.text = userProfile.getInt("todayDist",0).toString()+"m"
        binding.tvTodayKcal.text = userProfile.getInt("todayCal", 0).toString()+"kcal"

        val time = userProfile.getInt("todayTime",0)
        val h = (time / 3600)
        val m = (time - h * 3600) / 60
        val s = (time - h * 3600 - m * 60)
        val t = (if (h < 10) "0$h" else h).toString() + ":" + (if (m < 10) "0$m" else m) + ":" + if (s < 10) "0$s" else s

        binding.tvTodayTime.text = t

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.container = container!!
        binding = FragmentRunningBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRunning.setOnClickListener {
            val intent = Intent(container.context, MapActivity::class.java)
            startActivity(intent)
        }

    }

    companion object {

    }
}