package com.android.rideway_app.community

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.rideway_app.R
import com.android.rideway_app.databinding.FragmentBoardBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.community.BoardDataResponse
import com.android.rideway_app.retrofit.community.BoardService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BoardFragment : Fragment() {
    private var sorting_pos : Int = 0
    private var board_code : Int = 0
    private lateinit var board_name : String
    private var searching_option = "title"
    private var sorting_option = arrayOf("board_id","like")
    private lateinit var binding : FragmentBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("Board"){_, bundle ->
            board_code = bundle.getInt("code")!!
            board_name = bundle.getString("name")!!
            binding.tvBoardName.text = board_name
            println(board_code)
            val options = arrayOf("제목","작성자")
            val adapter = ArrayAdapter(binding.root.context,
                com.naver.maps.map.R.layout.support_simple_spinner_dropdown_item,options)

            binding.spSearchOptions.adapter = adapter

            //스피너 선택 이벤트 부여
            binding.spSearchOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                    searching_option = when(pos){
                        1 -> "user_id"
                        else -> "title"
                    }
                    Toast.makeText(binding.root.context,searching_option,Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    searching_option = "title"
                }

            }
            binding.sortingOptions.setOnClickListener { boardSort() }
            binding.btnSearch.setOnClickListener { getBoardListWithSearchKeyword() }

            getBoardList()
        }
    }

    //검색어 없을 때
    private fun getBoardList(){
        val retrofitAPI = RetrofitClient.getInstance().create(BoardService::class.java)
        retrofitAPI.getBoardList(board_code,0).enqueue(object : Callback<BoardDataResponse> {
            override fun onResponse(
                call: Call<BoardDataResponse>,
                response: Response<BoardDataResponse>
            ) {
                if(response.isSuccessful){
                    setBoard(response.body()!!.content)
                }else{

                }
            }

            override fun onFailure(call: Call<BoardDataResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    //검색어 있을 때
    private fun getBoardListWithSearchKeyword(){

    }

    private fun setBoard(list : List<BoardDataResponse.Content>){
        val adapter = BoardListAdapter(list,board_code,board_name)
        binding.boardRecyclerView.adapter = adapter
        binding.boardRecyclerView.layoutManager = LinearLayoutManager(context)
    }


    //정렬 옵션 설정
    private fun boardSort(){
        ++sorting_pos
        sorting_pos %= 2

        when(sorting_pos){
            0 -> {
                binding.sortingOptions.text = "최신순"
            }
            1 ->{
                binding.sortingOptions.text = "인기순"
            }
        }
    }

}