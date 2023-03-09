package com.android.rideway_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.Mypage.ProfileSet
import com.android.rideway_app.databinding.WeeklyUserItemBinding
import com.android.rideway_app.retrofit.myProfile.MainPageUserInfo

class WeeklyUserAdapter(var list: MutableList<MainPageUserInfo>): RecyclerView.Adapter<WeeklyUserAdapter.WeeklyAdapter>() {

    inner class WeeklyAdapter (private val binding: WeeklyUserItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(pos: Int){
            if(list.size!=1){
                binding.tvWeeklyEvent.text = list[pos].event
                binding.ciWeeklyImage.setImageBitmap(ProfileSet.stringToBitmap(list[pos].image_path))
                binding.tvUserNickPart.text = list[pos].nickname
                when(pos){
                    // 가장 오래 달린 사람일 때
                    0 ->{
                        val h = list[pos].weeklyData/3600
                        val m = (list[pos].weeklyData - h * 3600).toInt() / 60
                        val s = (list[pos].weeklyData - h * 3600 - m * 60).toInt()
                        val time = (if (h < 10) "0$h" else h).toString() + ":" + (if (m < 10) "0$m" else m) + ":" + if (s < 10) "0$s" else s

                        binding.tvWeeklyUnit.text = " 기록 : " + time
                    }
                    // 가장 멀리 달린 사람일 때
                    1 ->{
                        var distance : String
                        if(list[pos].weeklyData>1000){
                            val dist = (list[pos].weeklyData/100).toFloat()/10
                            distance = dist.toString()+"km"
                        }
                        else{
                            distance = list[pos].weeklyData.toString()+"m"
                        }
                        binding.tvWeeklyUnit.text = "기록 : "+distance
                    }
                    // 가장 칼로리를 소모를 많이 한 사람일 때때
                    2 ->{
                        binding.tvWeeklyUnit.text = "기록 : " + list[pos].weeklyData.toString()+"kcal"
                    }

                }
                val h = list[pos].total_time/3600
                val m = (list[pos].total_time - h * 3600).toInt() / 60
                val s = (list[pos].total_time - h * 3600 - m * 60).toInt()
                val time = (if (h < 10) "0$h" else h).toString() + ":" + (if (m < 10) "0$m" else m) + ":" + if (s < 10) "0$s" else s

                binding.tvWeeklyTime.text = time
                binding.tvWeeklyAddress.text = list[pos].address

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyAdapter {
        val view = WeeklyUserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WeeklyAdapter(view)
    }

    override fun onBindViewHolder(holder: WeeklyAdapter, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}