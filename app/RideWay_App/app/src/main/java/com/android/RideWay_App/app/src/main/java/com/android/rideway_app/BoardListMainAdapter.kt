
package com.android.rideway_app

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
import com.android.rideway_app.databinding.BoardListMainBinding
import com.android.rideway_app.retrofit.community.BoardDataResponse

class BoardListMainAdapter(var data : List<BoardDataResponse.Content>, var code : Int, var boardName : String) : RecyclerView.Adapter<BoardListMainAdapter.MyViewHolder>(){

    inner class MyViewHolder(binding : BoardListMainBinding) : RecyclerView.ViewHolder(binding.root){
        var bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding : BoardListMainBinding = BoardListMainBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var content : BoardDataResponse.Content = data[position]
        holder.bind.boardTime.text = content.regTime.split('T')[0]
        holder.bind.boardTitle.text = content.title
        holder.bind.boardWriter.text = content.userNickName

        holder.bind.boardListLayout.setOnClickListener{
            val intent = Intent(holder.bind.root.context, BoardDetailActivity::class.java)
            intent.putExtra("boardCode",code)
            intent.putExtra("boardNum",content.boardId)
            intent.putExtra("boardName",boardName)
            holder.bind.root.context.startActivity(intent)
        }

    }


}