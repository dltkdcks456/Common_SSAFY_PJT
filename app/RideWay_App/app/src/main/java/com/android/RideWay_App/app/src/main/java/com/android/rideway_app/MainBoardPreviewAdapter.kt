package com.android.rideway_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.databinding.BoardMainBinding
import com.android.rideway_app.databinding.MeetingListMainBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.community.BoardKind
import com.android.rideway_app.retrofit.community.BoardService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.create

class MainBoardPreviewAdapter(val data : List<BoardKind>) : RecyclerView.Adapter<MainBoardPreviewAdapter.MyViewHolder>() {
    inner class MyViewHolder(binding : BoardMainBinding) : RecyclerView.ViewHolder(binding.root){
        val bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding : BoardMainBinding = BoardMainBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind.tvBoardName.text = data[position].name

        CoroutineScope(Dispatchers.IO).launch {
            val list = RetrofitClient.getInstance().create(BoardService::class.java).getBoardListCoroutine(data[position].code,0)
            launch(Dispatchers.Main) {
                holder.bind.boardRecyclerView.adapter = BoardListMainAdapter(list.content,data[position].code,data[position].name)
                holder.bind.boardRecyclerView.layoutManager = LinearLayoutManager(holder.bind.root.context)
            }
        }
    }
}