package com.android.rideway_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.databinding.ProfileRecordItemBinding
import com.android.rideway_app.retrofit.weeklyUser.UserRecordData

class MyRecordAdapter(var list: MutableList<UserRecordData>): RecyclerView.Adapter<MyRecordAdapter.MyRecordAdapter>() {

    inner class MyRecordAdapter (private val binding: ProfileRecordItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(pos: Int){
            if(list.size!=1){
                when(pos){
                    0->{
                        binding.tvRecordTitle.text = "이번 주 나의 기록"

                        val h = list[pos].week_time/3600
                        val m = (list[pos].week_time - h * 3600).toInt() / 60
                        val s = (list[pos].week_time - h * 3600 - m * 60).toInt()
                        val time = (if (h < 10) "0$h" else h).toString() + ":" + (if (m < 10) "0$m" else m) + ":" + if (s < 10) "0$s" else s

                        var distance : String
                        if(list[pos].week_dist>1000){
                            val dist = (list[pos].week_dist/100).toFloat()/10
                            distance = dist.toString()+"km"
                        }
                        else{
                            distance = list[pos].week_dist.toString()+"m"
                        }

                        binding.tvMyProfileTime.text = time
                        binding.tvMyProfileDist.text = distance
                        binding.tvMyProfileCal.text = list[pos].week_cal.toString()+"kcal"
                    }
                    1->{
                        binding.tvRecordTitle.text = "나의 전체 라이딩 기록"

                        val h = list[pos].total_time/3600
                        val m = (list[pos].total_time - h * 3600).toInt() / 60
                        val s = (list[pos].total_time - h * 3600 - m * 60).toInt()
                        val time = (if (h < 10) "0$h" else h).toString() + ":" + (if (m < 10) "0$m" else m) + ":" + if (s < 10) "0$s" else s

                        var distance : String
                        if(list[pos].total_dist>1000){
                            val dist = (list[pos].total_dist/100).toFloat()/10
                            distance = dist.toString()+"km"
                        }
                        else{
                            distance = list[pos].total_dist.toString()+"m"
                        }

                        binding.tvMyProfileTime.text = time
                        binding.tvMyProfileDist.text = distance
                        binding.tvMyProfileCal.text = list[pos].total_cal.toString()+"kcal"

                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecordAdapter {
        val view = ProfileRecordItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyRecordAdapter(view)
    }

    override fun onBindViewHolder(holder: MyRecordAdapter, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}