package com.android.rideway_app.deal

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.databinding.DealImageListBinding
import com.android.rideway_app.databinding.DealListBinding

class DealImageListAdapter(val data : List<String>) : RecyclerView.Adapter<DealImageListAdapter.MyViewHolder>(){

    inner class MyViewHolder(binding : DealImageListBinding) : RecyclerView.ViewHolder(binding.root){
        var bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding : DealImageListBinding = DealImageListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val content = Base64.decode(data[position], Base64.DEFAULT);
        val bmp = BitmapFactory.decodeByteArray(content,0,content.size)
        holder.bind.itemImage.setImageBitmap(bmp)
    }

}