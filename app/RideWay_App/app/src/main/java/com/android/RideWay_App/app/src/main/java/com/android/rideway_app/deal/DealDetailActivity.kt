package com.android.rideway_app.deal

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.rideway_app.RetroFitConnection
import com.android.rideway_app.chat.ChatActivity
import com.android.rideway_app.databinding.ActivityDealDetailBinding
import com.android.rideway_app.mainapp.MainApplication
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.chat.ChatRoomResponseData
import com.android.rideway_app.retrofit.chat.ChatService
import com.android.rideway_app.retrofit.chat.CreateChatRoomData
import com.android.rideway_app.retrofit.deal.DealDetailResponse
import com.android.rideway_app.retrofit.deal.DealService
import com.android.rideway_app.retrofit.deal.ZzimDataResponse
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class DealDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDealDetailBinding
    private val retrofitAPI = RetrofitClient.getInstance().create(DealService::class.java)
    private lateinit var  userInfo : SharedPreferences
    private var dealId : Long = 0
    private var userId : Long = 0

    private lateinit var title : String
    private lateinit var product : String
    private lateinit var content : String
    private lateinit var price : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userInfo = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        //set deal and user info
        dealId = intent.getLongExtra("dealId",0)
        userId = userInfo.getInt("user_id",0).toLong()


        //carousel
        binding.viewPager.apply {
            clipChildren = false  // No clipping the left and right items
            clipToPadding = false  // Show the viewpager in full width without clipping the padding
            offscreenPageLimit = 3  // Render the left and right items
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect
        }

        //set buttom event
        binding.btnDeal.setOnClickListener {
            createChat()
        }
        binding.btnNoZzim.setOnClickListener {
            addFavorite()
        }
        binding.btnZzim.setOnClickListener {
            delFavorite()
        }
        binding.btnUpdate.setOnClickListener {
            val intent = Intent(this@DealDetailActivity, DealUpdateActivity::class.java)
            intent.putExtra("dealId",dealId)
            intent.putExtra("title",title)
            intent.putExtra("product",product)
            intent.putExtra("content",content)
            intent.putExtra("price",price)
            startActivity(intent)
        }
        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("?????? ??????")
                .setMessage("????????? ?????????????????????????")
                .setPositiveButton("???"
                ) { p0, p1 -> deleteDeal() }
                .setNegativeButton("?????????",null)
                .show()
        }
        binding.btnSaleComplete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("?????? ??????")
                .setMessage("????????? ?????????????????????????")
                .setPositiveButton("???"
                ) { p0, p1 -> dealComplete() }
                .setNegativeButton("?????????",null)
                .show()
        }
        getDealImage()
        getDetail()
        getFavorite()
    }

    override fun onResume() {
        super.onResume()
        getDealImage()
        getDetail()
        getFavorite()
    }
    private fun getDealImage(){
        retrofitAPI.getDealImage(dealId).enqueue(object : Callback<List<String>>{
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>,
            ) {
                if(response.isSuccessful){
                    binding.viewPager.adapter = DealImageListAdapter(response.body()!!)
                }else{
                    Toast.makeText(this@DealDetailActivity,"????????? ?????? ??????",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@DealDetailActivity,"???????????? ??????",Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun getDetail(){
        retrofitAPI.getDealDetail(userId,dealId).enqueue(object : Callback<DealDetailResponse> {
            override fun onResponse(
                call: Call<DealDetailResponse>,
                response: Response<DealDetailResponse>
            ) {
                if(response.isSuccessful){
                    val res = response.body()!!
                    val textView = binding.dealDetailContent
                    val formatter = DecimalFormat("###,###")
                    MainApplication.setOtherUserProfilePK(res.userId.toLong(),binding.ciProfileImage)
                    binding.dealDetailTitle.text = res.title
                    textView.setHtml(res.content, HtmlHttpImageGetter(textView))
                    binding.userName.text = res.userNickname
                    binding.visited.text = "????????? : " + res.visited.toString()
                    binding.writeDay.text = res.time
                    binding.product.text = "?????? : ${res.name}"
                    binding.price.text = "?????? : ${formatter.format(res.price)}???"
                    title = res.title
                    content = res.content
                    price = res.price.toString()
                    product = res.name

                    if(userId == res.userId.toLong()){
                        binding.btnOtherUser.visibility = View.INVISIBLE
                        binding.btnSameUser.visibility = View.VISIBLE
                    }
                    
                    //????????? ??????????????? ??????
                    if(!res.onSale){
                        binding.zzimLayout.visibility = View.VISIBLE
                        binding.btnZzim.visibility = View.GONE
                        binding.btnNoZzim.visibility = View.GONE
                        binding.comdeal.visibility = View.VISIBLE
                        binding.btnDeal.visibility = View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<DealDetailResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    private fun addFavorite(){
        retrofitAPI.addFav(dealId,userId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful)
                    getFavorite()
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
    private fun delFavorite(){
        retrofitAPI.delFav(dealId,userId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful)
                    getFavorite()
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
    private fun getFavorite(){
        retrofitAPI.getMyFavAboutIt(dealId,userId).enqueue(object : Callback<Boolean>{
            override fun onResponse(
                call: Call<Boolean>,
                response: Response<Boolean>
            ) {
                if(response.isSuccessful){

                    if (!response.body()!!){
                        binding.btnNoZzim.visibility = View.VISIBLE
                        binding.btnZzim.visibility = View.GONE

                    }else{
                        binding.btnNoZzim.visibility = View.GONE
                        binding.btnZzim.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@DealDetailActivity,"???????????? ??????",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun deleteDeal(){
        retrofitAPI.deleteDeal(dealId).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful) {
                    Toast.makeText(this@DealDetailActivity, "?????? ??????", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else
                    Toast.makeText(this@DealDetailActivity, "?????? ??????",Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@DealDetailActivity,"???????????? ??????",Toast.LENGTH_SHORT).show()

            }

        })
    }

    private fun dealComplete(){
        retrofitAPI.dealComplete(dealId).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful) {
                    Toast.makeText(this@DealDetailActivity, "?????? ?????? ???????????????.", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@DealDetailActivity, "?????? ??????", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@DealDetailActivity,"???????????? ??????",Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun createChat(){
        val token = MainApplication.getUserToken()
        val oppNickname = binding.userName.text.toString()
        val retrofitChat = RetrofitClient.getInstance().create(ChatService::class.java)
        retrofitChat.createDealChatRoom(
            CreateChatRoomData(
                token,
                MainApplication.prefs.getString("nickname","?????????")!!,
                oppNickname)
        ).enqueue(object :
            Callback<ChatRoomResponseData> {
            override fun onResponse(call: Call<ChatRoomResponseData>, response: Response<ChatRoomResponseData>) {
                if(response.isSuccessful){
                    val intent = Intent(this@DealDetailActivity, ChatActivity::class.java)
                    intent.putExtra("chatRoomId", response.body()!!.roomId)
                    intent.putExtra("chatType", 1)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<ChatRoomResponseData>, t: Throwable) {
                Toast.makeText(this@DealDetailActivity, "???????????? ?????? ????????? ?????????????????????.\n????????? ?????? ??????????????????", Toast.LENGTH_LONG).show()
            }
        })
    }
}