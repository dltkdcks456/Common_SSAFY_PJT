package com.android.rideway_app

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.Mypage.ProfileSet
import com.android.rideway_app.databinding.FragmentCommunityBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.community.BoardKind
import com.android.rideway_app.retrofit.community.BoardService
import com.android.rideway_app.retrofit.fileUpload.FileService
import com.android.rideway_app.retrofit.login.LoginService
import com.android.rideway_app.retrofit.login.OneUserDataResponse
import com.android.rideway_app.retrofit.meeting.CommunityService
import com.android.rideway_app.retrofit.myProfile.MainPageUserInfo
import com.android.rideway_app.retrofit.weeklyUser.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


class CommunityFragment : Fragment() {

    private lateinit var container : ViewGroup
//    private lateinit var community_list : List<String>
    private lateinit var binding : FragmentCommunityBinding
    private lateinit var mutableList: MutableList<MainPageUserInfo>
    private lateinit var boardKindList : List<BoardKind>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //이 때 커뮤니티 목록을 가져옴
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.container = container!!
        binding = FragmentCommunityBinding.inflate(inflater,container,false)
//        getCommunityList()

        // Inflate the layout for this fragment
        return binding.root
    }

    /*
    * 해야할 일 : 우선 가장 이번 주에 열심히 달린 사람을 보여줌 -> 만약 0일 시 미리 만들어 둔 비어있는 DataClass를 기준으로 리스트를 짜서 어뎁터로 보내줌
    * 0이 아니라면? -> 각자 분야에서 1등 한 사람의 닉네임을 가지고 프로필을 가져옴 -> 프로필 이미지, 주소(시 구 까지만)를 가져옴 (/user/search에 있을 거임),
    *                   recode/getUserInfo를 통해서 total 시간을 가져옴, 이거 다 합쳐서 (닉네임, 이미지 주소, 이번 주 기록(거리, 칼로리, 시간), total시간, 주소)
    * 이걸 리스트로 만들어서 어뎁터로 보내주고 표시해준다 이말이야
    *
    *
    * */

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch{
            boardKindList = RetrofitClient.getInstance().create(BoardService::class.java).getBoardCodeCoroutine()

            launch(Dispatchers.Main) {
                binding.boardRecyclerView.adapter = MainBoardPreviewAdapter(boardKindList)
            }
        }
        //carousel
        binding.communityRecyclerView.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }
        getBestWeek()
//        getBestTime()
        getCommunity()
        mutableList = mutableListOf()

        mutableList.add(MainPageUserInfo("이번 주 아직 리스트가 없어요!", "", "", -1, 0,"",0))

        binding.rvWeeklyUser.apply {
            adapter = WeeklyUserAdapter(mutableList)
            layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun getCommunity(){
        CoroutineScope(Dispatchers.IO).launch {
            val res = async {
                val retrofitAPI = RetrofitClient.getInstance().create(CommunityService::class.java)
                retrofitAPI.getListCoroutine(0).content
            }
            launch(Dispatchers.Main) {
                val list = res.await()
                binding.communityRecyclerView.adapter = MeetingListAdapter(list)
            }
        }
    }

    private fun getBestWeek(){
        var retrofitAPI = RetrofitClient.getInstance().create(WeeklyService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val bestTime = retrofitAPI.getBestTime()[0]
                val bestDist = retrofitAPI.getBestDist()[0]
                val bestCal = retrofitAPI.getBestCal()[0]
                val users : List<MainPageUserInfo> = getBestUsers(bestTime, bestDist, bestCal)

                launch(Dispatchers.Main){
                    bindingAdpater(users.get(0),users.get(1),users.get(2))
                }

            }catch (e : Exception){
                e.printStackTrace()
            }

        }


    }

    private suspend fun getBestUsers(bestTime : WeeklyTimeDataItem, bestDist : WeeklyDistDataItem, bestCal : WeeklyCalDataItem) : List<MainPageUserInfo>{
        var retrofitAPI = RetrofitClient.getInstance().create(WeeklyService::class.java)

        // 각 종목의 유저들의 정보를 가져옴
        var timeUser = retrofitAPI.searchOneUser(bestTime.nickname)
        var distUser = retrofitAPI.searchOneUser(bestDist.nickname)
        var calUser = retrofitAPI.searchOneUser(bestCal.nickname)

        // 각 종목의 total 시간을 가져옴
        val timeUserTotalTime = retrofitAPI.getWeekUserRecord(bestTime.nickname).total_time
        val distUserTotalTime = retrofitAPI.getWeekUserRecord(bestDist.nickname).total_time
        val calUserTotalTime = retrofitAPI.getWeekUserRecord(bestCal.nickname).total_time

        // 가져온 정보의 값으로 byteStream을 받아옴
        val byteValue1 = retrofitAPI.downloadImagePk(timeUser.user_id).byteStream()
        val byteValue2 = retrofitAPI.downloadImagePk(distUser.user_id).byteStream()
        val byteValue3 = retrofitAPI.downloadImagePk(calUser.user_id).byteStream()

        val imageList = listOf(BitmapFactory.decodeStream(byteValue1), BitmapFactory.decodeStream(byteValue2), BitmapFactory.decodeStream(byteValue3))

        var mutableStringList = mutableListOf<String?>()

        // 각자 null인지 확인해서 null이면 기본 이미지를 세팅해줌
        for(item in imageList){
            var bitmapToString : String?

            if(item == null){
                bitmapToString = ProfileSet.bitmapToString(BitmapFactory.decodeResource(resources,R.drawable.img_default_profile))
            }
            else{
                bitmapToString = ProfileSet.bitmapToString(item)
            }
            mutableStringList.add(bitmapToString)
        }

        val item1 = MainPageUserInfo("이번 주 가장 멀리 달린 사람!", bestTime.nickname, mutableStringList.get(0)!!,
            bestTime.week_time, timeUserTotalTime, timeUser.si + " " + timeUser.gun, timeUser.user_id)

        val item2 = MainPageUserInfo("이번 주 가장 멀리 달린 사람!", bestDist.nickname, mutableStringList.get(1)!!,
            bestDist.week_dist, distUserTotalTime, distUser.si + " " + distUser.gun, timeUser.user_id)

        val item3 = MainPageUserInfo("이번 주 가장 칼로리를 불태운 사람!", bestCal.nickname, mutableStringList.get(2)!!,
            bestCal.week_cal, calUserTotalTime, calUser.si + " " + calUser.gun, timeUser.user_id)

        return listOf(item1,item2,item3);
    }

    private fun bindingAdpater(first : MainPageUserInfo, second: MainPageUserInfo, third: MainPageUserInfo){

        mutableList.clear()

        mutableList.add(first)
        mutableList.add(second)
        mutableList.add(third)

        binding.rvWeeklyUser.apply {
            adapter = WeeklyUserAdapter(mutableList)
            layoutManager = LinearLayoutManager(container.context, LinearLayoutManager.HORIZONTAL, false)
        }

    }

    //리스트를 토대로 리사이클러 뷰를 채운다.
//    private fun setRecyclerView(board_kind_list : List<BoardKind>){
//        val adapter : CommunityListAdapter = CommunityListAdapter(this, board_kind_list)
//        binding.recyclerView.adapter = adapter
//        binding.recyclerView.layoutManager = LinearLayoutManager(context)
//    }

    fun setBoardInformation(code : Int , name : String){
        println("set Board Information")
        setFragmentResult("Board" , bundleOf(
            "name" to name,
            "code" to code
        ))
    }
}