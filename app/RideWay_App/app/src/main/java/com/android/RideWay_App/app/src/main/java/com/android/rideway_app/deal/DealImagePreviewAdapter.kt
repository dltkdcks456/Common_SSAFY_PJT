package com.android.rideway_app.deal

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.R
import com.android.rideway_app.databinding.DealImagePreviewListBinding
import com.android.rideway_app.databinding.DealListBinding

class DealImagePreviewAdapter(val data : MutableList<Uri>) : RecyclerView.Adapter<DealImagePreviewAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding : DealImagePreviewListBinding) : RecyclerView.ViewHolder(binding.root){
        var bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding : DealImagePreviewListBinding = DealImagePreviewListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //이미지 세팅
        holder.bind.imageView3.setImageURI(data[position])

        //click event / 삭제
        holder.bind.imageView3.setOnClickListener {
            println("${position+1}번째 이미지 제거")
            data.removeAt(position)
            this@DealImagePreviewAdapter.notifyDataSetChanged()
        }
    }
}