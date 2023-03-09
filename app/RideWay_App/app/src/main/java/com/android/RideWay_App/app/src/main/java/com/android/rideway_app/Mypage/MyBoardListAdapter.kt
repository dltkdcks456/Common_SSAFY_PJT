
package com.android.rideway_app.Mypage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.MainActivity
import com.android.rideway_app.community.BoardDetailActivity
import com.android.rideway_app.databinding.BoardListBinding
import com.android.rideway_app.retrofit.community.BoardDataResponse
import com.android.rideway_app.retrofit.myProfile.MyBoard

class MyBoardListAdapter(var data : List<MyBoard>) : RecyclerView.Adapter<MyBoardListAdapter.MyViewHolder>(){

    inner class MyViewHolder(binding : BoardListBinding) : RecyclerView.ViewHolder(binding.root){
        var bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding : BoardListBinding = BoardListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var content = data[position]

        holder.bind.boardNum.text = content.board_id.toString()
        holder.bind.boardTime.text = content.board_reg_time
        holder.bind.boardTitle.text = content.board_title
        holder.bind.boardWriter.text = content.board_code_name

        holder.bind.boardListLayout.setOnClickListener{
            val intent = Intent(holder.bind.root.context, BoardDetailActivity::class.java)
            intent.putExtra("boardCode",content.board_code)
            intent.putExtra("boardNum",content.board_id)
            intent.putExtra("boardName",content.board_code_name)
            holder.bind.root.context.startActivity(intent)
        }

    }


}