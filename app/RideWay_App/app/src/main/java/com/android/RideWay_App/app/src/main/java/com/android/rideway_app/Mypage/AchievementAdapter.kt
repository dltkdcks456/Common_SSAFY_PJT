package com.android.rideway_app.Mypage

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.databinding.AchieveItemListBinding

class AchievementAdapter(private val achieved: Map<Int, List<Boolean>>) : RecyclerView.Adapter<AchievementAdapter.MyAchieve>() {

    private val data = mapOf(
        "주행 거리" to listOf("마라톤", "서울 한바퀴", "국토종주"),
        "게시판 활동" to listOf("눈치 보는 중", "함께해요", "지박령"),
        "주행 시간" to listOf("비기너", "슈퍼 루키", "베테랑"),
        "코스 추천" to listOf("나만 알던 길", "은둔 고수", "RideWay"),
        "중고거래" to listOf("아나바다", "장사 시작", "보부상")
    )

    inner class MyAchieve(private val binding: AchieveItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            Log.d("아니니닛", pos.toString())
            binding.tvAchieveName.text = data.keys.elementAt(pos)
            binding.rvAchievementsBadge.apply {
                adapter = AchievementImageAdapter(data.values.elementAt(pos), achieved.values.elementAt(pos) ,pos)
                layoutManager = LinearLayoutManager(binding.rvAchievementsBadge.context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAchieve {
        val view = AchieveItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyAchieve(view)
    }

    override fun onBindViewHolder(holder: MyAchieve, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
