
package com.android.rideway_app.community

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.CommunityFragment
import com.android.rideway_app.MainActivity
import com.android.rideway_app.R
import com.android.rideway_app.databinding.CommunityListBinding
import com.android.rideway_app.deal.DealActivity
import com.android.rideway_app.retrofit.community.BoardKind

class CommunityListAdapter(fragment: CommunityFragment , board_kind_list : List<BoardKind>) : RecyclerView.Adapter<CommunityListAdapter.MyViewHolder>(){
    private lateinit var fragment : CommunityFragment
    val data = mapOf(
        "공지사항" to null,
        "커뮤니티" to board_kind_list,
        "중고장터" to null,
        "추천 경로" to null,
        "유저 검색" to null,
        "모임 게시판" to null
    )
    init {
        this.fragment = fragment
    }
    inner class MyViewHolder(binding : CommunityListBinding) : RecyclerView.ViewHolder(binding.root){

        val bind = binding
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //뷰홀더 객체 생성
        val binding : CommunityListBinding = CommunityListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val key = data.keys.elementAt(position)
        val dt : List<BoardKind>? = data.get(key)

        if(dt == null){//하위 리스트가 null일 경우 click 시 즉시 이동
            //일단 버튼 숨김
            holder.bind.cmBtn.visibility = View.GONE

            holder.bind.layout01.setOnClickListener {
                if(key.equals("중고장터")) {
                    val intent = Intent(holder.bind.root.context, DealActivity::class.java)
                    holder.bind.root.context.startActivity(intent)
                }
            }

        }else{//하위 리스트가 존재 할 경우 click 시 확장 리스트 오픈
            holder.bind.layout01.setOnClickListener {
                if(holder.bind.hiddenList.visibility == View.VISIBLE){
                    holder.bind.hiddenList.visibility = View.GONE

                    holder.bind.cmBtn.animate().apply {
                        duration = 200
                        rotation(0f)
                    }
                }else{
                    holder.bind.hiddenList.visibility = View.VISIBLE

                    holder.bind.cmBtn.animate().apply {
                        duration = 200
                        rotation(180f)
                    }
                }
            }
        }
        holder.bind.communityTitle.text = key

        data.values.elementAt(position)?.forEach {
            var sub_code = -1
            lateinit var sub_title : String
            //확장 리스트에 담길 view 객체 생성
            val view = TextView(holder.bind.root.context).apply{
                text = it.name
                textSize = 20f
                setPadding(10,10,5,10)
                sub_code = it.code
                sub_title = it.name
            }
            //확장 리스트 클릭 이벤트 추가
            view.setOnClickListener {
                when(key){
                    "커뮤니티"->{
                        var intent = Intent(holder.bind.root.context, BoardActivity::class.java)
                        intent.putExtra("code",sub_code)
                        intent.putExtra("name",sub_title)
                        holder.bind.root.context.startActivity(intent)
                    }
                }
            }
            //확장리스트에 객체 추가
            holder.bind.hiddenList.addView(view)
        }
    }

}