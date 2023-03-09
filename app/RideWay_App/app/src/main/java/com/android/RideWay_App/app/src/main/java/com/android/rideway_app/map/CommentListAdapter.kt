package com.android.rideway_app.map

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.databinding.CommentListBinding
import com.android.rideway_app.mainapp.MainApplication
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.community.BoardService
import com.android.rideway_app.retrofit.fileUpload.CourseDetailResponse
import com.android.rideway_app.retrofit.fileUpload.FileService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentListAdapter(val data: List<CourseDetailResponse.Comment>, private val detailChannel: CourseCommentChannel) : RecyclerView.Adapter<CommentListAdapter.MyViewHolder>() {
    inner class MyViewHolder(binding : CommentListBinding) : RecyclerView.ViewHolder(binding.root){
        val bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CommentListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val content = data[position]
        holder.bind.nickName.text = content.userNickname
        holder.bind.date.text = content.time
        holder.bind.content.text = content.content
        MainApplication.setOtherUserProfilePK(content.userId.toLong(),holder.bind.profileImage)
        if(content.userId != MainApplication.getUserId()){
            holder.bind.update.visibility = View.GONE
            holder.bind.delete.visibility = View.GONE
        }
        holder.bind.update.setOnClickListener {
            if(holder.bind.updateLayout.visibility == View.GONE){
                holder.bind.updateLayout.visibility = View.VISIBLE
                holder.bind.update.text = "접기"
            }else{
                holder.bind.updateLayout.visibility = View.GONE
                holder.bind.update.text = "수정"
            }
        }

        holder.bind.btnCommentUpdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                val data = hashMapOf<String,String>()
                data.put("course_board_comment_id",content.courseBoardCommentId.toString())
                data.put("content",holder.bind.etComment.text.toString())
                RetrofitClient.getInstance().create(FileService::class.java).updateComment(data)
                holder.bind.etComment.setText("")

                detailChannel.setList()
            }
        }

        holder.bind.delete.setOnClickListener {
            AlertDialog.Builder(holder.bind.root.context)
                .setTitle("댓글 삭제")
                .setMessage("댓글을 삭제하시겠습니까?")
                .setPositiveButton("네"
                ) { p0, p1 -> deleteComment(content.courseBoardCommentId.toLong()) }
                .setNegativeButton("아니요",null)
                .show()
        }
    }

    private fun deleteComment(courseBoardCommentId : Long){
        CoroutineScope(Dispatchers.IO).launch{
            RetrofitClient.getInstance().create(FileService::class.java).deleteComment(courseBoardCommentId)
            detailChannel.setList()
        }
    }
}