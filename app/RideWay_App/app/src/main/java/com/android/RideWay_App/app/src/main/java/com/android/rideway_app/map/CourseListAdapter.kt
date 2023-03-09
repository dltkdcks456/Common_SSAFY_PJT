
package com.android.rideway_app.map

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.databinding.BoardListBinding
import com.android.rideway_app.retrofit.fileUpload.CourseListDataResponse

class CourseListAdapter(var data : List<CourseListDataResponse.Content>) : RecyclerView.Adapter<CourseListAdapter.MyViewHolder>(){

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

        holder.bind.boardNum.text = content.courseBoardId.toString()
        holder.bind.boardTime.text = content.regTime
        holder.bind.boardTitle.text = content.title
        holder.bind.boardWriter.text = content.userNickname

        holder.bind.boardListLayout.setOnClickListener{
            val intent = Intent(holder.bind.root.context,CourseDetailActivity::class.java)
            intent.putExtra("courseBoardId",content.courseBoardId)
            holder.bind.root.context.startActivity(intent)
        }

    }


}