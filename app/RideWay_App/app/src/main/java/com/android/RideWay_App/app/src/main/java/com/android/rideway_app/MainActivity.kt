package com.android.rideway_app

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.rideway_app.Mypage.ProfileSet
import com.android.rideway_app.community.BoardActivity
import com.android.rideway_app.community.BoardFragment
import com.android.rideway_app.databinding.ActivityMainBinding
import com.android.rideway_app.deal.DealActivity
import com.android.rideway_app.map.CourseActivity
import com.android.rideway_app.meeting.MeetingActivity
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.community.BoardKind
import com.android.rideway_app.retrofit.community.BoardService
import com.android.rideway_app.retrofit.fileUpload.FileService
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private var backKeyPressedTime: Long = 0

    // 첫 번째 뒤로가기 버튼을 누를때 표시
    private var toast: Toast? = null

    lateinit var binding : ActivityMainBinding
    lateinit var response : String
    private lateinit var userProfile: SharedPreferences
    init{
        instance = this
    }
    companion object{
        private lateinit var instance : MainActivity
        fun getInstance() :MainActivity{
            return instance!!
        }
    }

    override fun onResume() {
        super.onResume()
        val mNow = System.currentTimeMillis();
        val mDate = Date(mNow);
        val dateFormat2 = SimpleDateFormat("dd")
        val getTime: String = dateFormat2.format(mDate)


        if(userProfile.getString("date", "32") != getTime){
            userProfile.edit().putString("date", getTime).apply()
            userProfile.edit().putInt("todayTime",0).apply()
            userProfile.edit().putInt("todayDist",0).apply()
            userProfile.edit().putInt("todayCal",0).apply()
        }

        binding.tvDrawNickname.text = userProfile.getString("nickname", null)
        binding.tvDrawEmail.text = userProfile.getString("email", null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //유저 이미지
//        MainApplication.setOtherUserProfilePK(MainApplication.getUserId().toLong(),binding.userProfileImage)
        var bottomNav = binding.bottomNav as BottomNavigationView

        val toolbar = binding.tbMainToolBar
        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)
        Log.d("token", userProfile.getString("token","dsfsdf")!!)

        getMyProfileImage()

        response = intent.getStringExtra("response").toString()

        // OnNavigationItemSelectedListener를 통해 탭 아이템 선택 시 이벤트를 처리
        // navi_menu.xml 에서 설정했던 각 아이템들의 id를 통해 알맞은 프래그먼트로 변경하게 한다.
        bottomNav.run { setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.if_community -> {
                    // 다른 프래그먼트 화면으로 이동하는 기능
                    toolbar.visibility = View.VISIBLE
                    binding.toolbarTitle.text = "커뮤니티"
                    val communityFragment = CommunityFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, communityFragment).commit()
                }
                R.id.if_running -> {
                    toolbar.visibility = View.VISIBLE
                    binding.toolbarTitle.text = "RideWay"
                    val runningFragment = RunningFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, runningFragment).commit()
                }
                R.id.if_profile -> {
                    toolbar.visibility = View.VISIBLE
                    binding.toolbarTitle.text = "마이페이지"
                    val mypageFragment = MypageFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fl_container, mypageFragment).commit()
                }
            }
            true
        }
            selectedItemId = R.id.if_community
        }
        //게시판 리스트
        getCommunityList()

        //drawer
        binding.ibMenu.setOnClickListener {
            binding.drawer.openDrawer(Gravity.RIGHT)
        }

        //menu intent

        binding.goDeal.setOnClickListener {
            val intent = Intent(this,DealActivity::class.java)
            startActivity(intent)
        }
        binding.goRoute.setOnClickListener {
            val intent = Intent(this,CourseActivity::class.java)
            startActivity(intent)
        }

        binding.goMeeting.setOnClickListener {
            val intent = Intent(this,MeetingActivity::class.java)
            startActivity(intent)
        }
        //하위 리스트 출력
        binding.goBoard.setOnClickListener {

            if(binding.hiddenList.visibility == View.VISIBLE){
                binding.hiddenList.visibility = View.GONE

                binding.cmBtn.animate().apply {
                    duration = 200
                    rotation(0f)
                }
            }else{
                binding.hiddenList.visibility = View.VISIBLE

                binding.cmBtn.animate().apply {
                    duration = 200
                    rotation(180f)
                }
            }
        }
    }
    override fun onBackPressed() {
        // 기존 뒤로가기 버튼의 기능을 막기위해 주석처리 또는 삭제
        // super.onBackPressed();

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
            toast!!.show()
            return
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish()
            toast!!.cancel()
        }
    }


    //하위 프래그먼트에서 프래그먼트를 교체시킬 수 있도록 상위 액티비티에서 교체가능한 함수를 제공함
    fun changeBoardFragment(index: Int){
        when(index){
            1 -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fl_container, BoardFragment())
                    .commit()
            }
        }
    }

    private fun getMyProfileImage(){
        val retrofitAPI = RetrofitClient.getInstance().create(FileService::class.java)

        retrofitAPI.downloadImage(userProfile.getString("id",null)!!).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful && response.body() != null){
                    val byteValue = response.body()!!.byteStream()
                    val bitmap = BitmapFactory.decodeStream(byteValue)
                    if(bitmap == null){
                        val bitmapToString = ProfileSet.bitmapToString(BitmapFactory.decodeResource(resources,R.drawable.img_default_profile))
                        userProfile.edit().putString("profileImage", bitmapToString).apply()
                        // 드로어 레이아웃에 프로필 이미지 넣는 부분
                        binding.ciCommunityProfileImage.setImageBitmap(ProfileSet.stringToBitmap(userProfile.getString("profileImage","null")))
                    }
                    else{
                        val bitmapToString = ProfileSet.bitmapToString(bitmap)
                        userProfile.edit().putString("profileImage", bitmapToString).apply()
                        // 드로어 레이아웃에 프로필 이미지 넣는 부분
                        binding.ciCommunityProfileImage.setImageBitmap(ProfileSet.stringToBitmap(userProfile.getString("profileImage","null")))
                    }

                }
                else{
                    Toast.makeText(this@MainActivity,"이미지 로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@MainActivity,"네트워크 문제가 발생하였습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //게시판 종류를 불러온다
    private fun getCommunityList(){
        var retrofitAPI = RetrofitClient.getInstance().create(BoardService::class.java)

        retrofitAPI.getBoardCode().enqueue(object : Callback<List<BoardKind>>{
            override fun onResponse(
                call: Call<List<BoardKind>>,
                response: Response<List<BoardKind>>
            ) {
                if(response.isSuccessful){
                    var content : ArrayList<BoardKind> = response.body()!! as ArrayList<BoardKind>
                    content.forEach {
                        var sub_code = -1
                        lateinit var sub_title : String
                        //확장 리스트에 담길 view 객체 생성
                        val view = TextView(this@MainActivity).apply{
                            text = it.name
                            textSize = 20f
                            setPadding(10,10,5,10)
                            sub_code = it.code
                            sub_title = it.name
                        }
                        //확장 리스트 클릭 이벤트 추가
                        view.setOnClickListener {
                            var intent = Intent(this@MainActivity, BoardActivity::class.java)
                            intent.putExtra("code",sub_code)
                            intent.putExtra("name",sub_title)
                            startActivity(intent)
                        }
                        //확장리스트에 객체 추가
                        binding.hiddenList.addView(view)
                    }
                }else{
                    Toast.makeText(this@MainActivity,"커뮤니티 목록을 불러올 수 없습니다.",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<BoardKind>>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@MainActivity,"네트워크 오류",Toast.LENGTH_SHORT).show()
            }
        })
    }
}