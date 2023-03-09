package com.android.rideway_app.community

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.databinding.ActivityBoardBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.community.BoardDataResponse
import com.android.rideway_app.retrofit.community.BoardService
import com.naver.maps.map.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardActivity : AppCompatActivity() {
    private val retrofitAPI = RetrofitClient.getInstance().create(BoardService::class.java)
    private lateinit var binding : ActivityBoardBinding
    private var sorting_pos : Int = 0
    private var board_code : Int = 0
    private lateinit var board_name : String
    private var searching_option = "title"
    private var sorting_option = arrayOf("board_id","like")
    private var boardList = mutableListOf<BoardDataResponse.Content>()
    private var keyword : String = ""
    private var page = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        board_code = intent.getIntExtra("code",0)
        board_name = intent.getStringExtra("name").toString()
        binding.tvBoardName.text = board_name

        val options = arrayOf("제목","작성자")
        val adapter = ArrayAdapter(binding.root.context,
            R.layout.support_simple_spinner_dropdown_item,options)

        binding.spSearchOptions.adapter = adapter

        //스피너 선택 이벤트 부여
        binding.spSearchOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                searching_option = when(pos) {
                    1 -> "user_id"
                    else -> "title"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                searching_option = "title"
            }

        }
        binding.sortingOptions.setOnClickListener { boardSort() }
        binding.btnSearch.setOnClickListener {
            keyword = binding.etSearchKeyword.text.toString()
            this.page = 0
            getBoardList(this.page)
        }

        binding.btnGoWrite.setOnClickListener {
            var intent = Intent(this,BoardWriteActivity::class.java)
            intent.putExtra("board_code",board_code.toString())
            startActivity(intent)
        }
        // 스크롤 끝 감지
        binding.boardRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!binding.boardRecyclerView.canScrollVertically(1)) {
                    getBoardList(++page)
                }
            }
        })
        getBoardList(this.page)
    }

    override fun onResume(){
        super.onResume()
        this.page = 0
        getBoardList(page)
    }

    //검색어 없을 때
    private fun getBoardList(boardPage : Int){
        
        if(!keyword.equals("")){ //검색어가 있을 경우
            retrofitAPI.getBoardSearchList(board_code,keyword,boardPage).enqueue(object : Callback<BoardDataResponse> {
                override fun onResponse(
                    call: Call<BoardDataResponse>,
                    response: Response<BoardDataResponse>,
                ) {
                    if(response.isSuccessful){
                        if(boardPage == 0){
                            //리스트 새로 초기화
                            boardList = response.body()!!.content
                            setBoard()
                        }else{
                            boardList.addAll(response.body()!!.content)
                            binding.boardRecyclerView.adapter!!.notifyDataSetChanged()
                            Toast.makeText(this@BoardActivity,"목록을 추가로 불러왔습니다.",Toast.LENGTH_SHORT).show()
                        }

                    }else{
                        Toast.makeText(this@BoardActivity,"리스트를 불러오지 못했습니다.",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BoardDataResponse>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@BoardActivity,"네트워크 오류",Toast.LENGTH_SHORT).show()
                }
            })

        }else{ //검색어가 없을 경우
            retrofitAPI.getBoardList(board_code,boardPage).enqueue(object : Callback<BoardDataResponse> {
                override fun onResponse(
                    call: Call<BoardDataResponse>,
                    response: Response<BoardDataResponse>,
                ) {
                    if(response.isSuccessful){
                        if(boardPage == 0){
                            //리스트 새로 초기화
                            boardList = response.body()!!.content
                            setBoard()
                        }else{
                            boardList.addAll(response.body()!!.content)
                            binding.boardRecyclerView.adapter!!.notifyDataSetChanged()
                            Toast.makeText(this@BoardActivity,"목록을 추가로 불러왔습니다.",Toast.LENGTH_SHORT).show()
                        }

                    }else{
                        Toast.makeText(this@BoardActivity,"리스트를 불러오지 못했습니다.",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BoardDataResponse>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@BoardActivity,"네트워크 오류",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }



    private fun setBoard(){
        val adapter = BoardListAdapter(boardList,board_code,board_name)
        binding.boardRecyclerView.adapter = adapter
        binding.boardRecyclerView.layoutManager = LinearLayoutManager(this)
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