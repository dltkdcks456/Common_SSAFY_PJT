package com.android.rideway_app.Mypage

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.R
import com.android.rideway_app.databinding.AchieveImageListBinding

class AchievementImageAdapter(private val data:List<String>, private val achieved:List<Boolean>, private val parentPost: Int) : RecyclerView.Adapter<AchievementImageAdapter.MyView02>() {

    val images = listOf( listOf(R.drawable.distance3, R.drawable.distance2, R.drawable.distance1),
                          listOf(R.drawable.community3, R.drawable.community2, R.drawable.community1),
                          listOf(R.drawable.time3, R.drawable.time2, R.drawable.time1),
                          listOf(R.drawable.course3, R.drawable.course2, R.drawable.course1),
                          listOf(R.drawable.sell3, R.drawable.sell2, R.drawable.sell1),)

    inner class MyView02(private val binding: AchieveImageListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            if(achieved[pos]) binding.imgBadges.setImageResource(images[parentPost][pos])
            else binding.imgBadges.setImageResource(R.drawable.lock)
            binding.tvAchieveTitle.text = data[pos]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView02 {
        val view = AchieveImageListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyView02(view)
    }

    override fun onBindViewHolder(holder: MyView02, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}