package com.android.rideway_app.Mypage

import android.R
import android.app.Activity
import android.content.CursorLoader
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.android.rideway_app.databinding.ActivityProfileModifyBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.fileUpload.FileService
import com.android.rideway_app.retrofit.login.LoginService
import com.android.rideway_app.retrofit.login.ModifyUserProfileData
import com.android.rideway_app.retrofit.login.UserProfileData
import com.android.rideway_app.retrofit.region.DongData
import com.android.rideway_app.retrofit.region.GunData
import com.android.rideway_app.retrofit.region.RegionService
import com.android.rideway_app.retrofit.region.SiData
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.regex.Pattern


class ProfileModifyActivity : AppCompatActivity() {
    lateinit var binding : ActivityProfileModifyBinding
    lateinit var userProfile: SharedPreferences

    lateinit var siList : SiData
    lateinit var gunList : GunData
    lateinit var dongList : DongData

    lateinit var si : String
    lateinit var gun : String
    lateinit var dong : String

    // 딱 한번만 군과 동의 리스트 값을 지정하기 위해서 있는 플래그
    var gunSelected = false
    var dongSelected = false

    // 유효성 검사 => 자전거 무게 6이상 인지, 몸무게가 30이상인지, 닉네임 중복 여부 확인,
    var nickNameCheck = true
    var weightCheck = true
    var bikeWeightCheck = true

    // 이미지 url
    var imageurl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userProfile = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        // si 값을 모두 가져옴
        getAllSi()

        // 이미지와 클릭 리스너 추가'
        binding.ciProfileImage.setImageBitmap(ProfileSet.stringToBitmap(userProfile.getString("profileImage","null")))

        binding.ciProfileImage.setOnClickListener {
            when {
                // 갤러리 접근 권한이 있는 경우
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                -> {
                    navigateGallery()
                }

                // 갤러리 접근 권한이 없는 경우 & 교육용 팝업을 보여줘야 하는 경우
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                -> {
                    showPermissionContextPopup()
                }

                // 권한 요청 하기(requestPermissions) -> 갤러리 접근(onRequestPermissionResult)
                else -> requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000
                )
            }
        }

        // 툴바의 제목과, 뒤로가기 버튼 설정
        binding.toolbarTitle.text = "프로필 수정"

        binding.ibBack.setOnClickListener {
            AlertDialog.Builder(this@ProfileModifyActivity)
                .setTitle("수정 취소")
                .setMessage("\n수정을 그만두시겠습니까?")
                .setPositiveButton("네") { _, _ -> finish()}
                .setNegativeButton("아니오"){_,_ -> }
                .setCancelable(false)
                .create()
                .show()
        }

        // open 여부
        binding.sOpen.isChecked = userProfile.getBoolean("open", true)

        // 닉네임 기본값
        binding.etModifyNickName.setText(userProfile.getString("nickname",null))

        binding.etModifyNickName.addTextChangedListener {
            if(binding.etModifyNickName.text.isEmpty()) nickNameCheck = false
            else{
                nickNameCheck = nameCheck(binding.etModifyNickName.text.toString())
            }

        }

        // 몸무게 기본값
        binding.etModifyWeight.setText(userProfile.getInt("weight",65).toString())

        // 몸무게 비교
        binding.etModifyWeight.addTextChangedListener {
            if(binding.etModifyWeight.text.toString().isEmpty()){
                weightCheck = false
            }
            else{
                // 만약 원래 들어있던 몸무게 값이랑 같다면 그냥 true로 해주고 아니라면 30kg 이상인지 확인
                if(binding.etModifyWeight.text.toString().toInt() == userProfile.getInt("weight", 65)) weightCheck =true
                else weightCheck = binding.etModifyWeight.text.toString().toInt() >= 30
            }
        }

        // 자전거 무게
        binding.etModifyBikeWeight.setText(userProfile.getInt("cycle_weight",12).toString())

        // 자전거 무게 비교
        binding.etModifyBikeWeight.addTextChangedListener {
            if(binding.etModifyBikeWeight.text.toString().isEmpty()){
                bikeWeightCheck = false
            }
            else{
                // 만약 원래 들어있던 자전거 무게 값이랑 같다면 그냥 true로 해주고 아니라면 6kg 이상인지 확인
                if(binding.etModifyBikeWeight.text.toString().toInt() == userProfile.getInt("cycle_weight", 12)) bikeWeightCheck =true
                else bikeWeightCheck = binding.etModifyBikeWeight.text.toString().toInt() >= 6
            }


        }



        // 완료 버튼을 누르면 몸무게, 자전거 무게, 닉
        binding.tvComplete.setOnClickListener {

            // 닉네임 중복 체크
            if(binding.etModifyNickName.text.toString() != userProfile.getString("nickname", null)){
                nickNameCheck(binding.etModifyNickName.text.toString())
            }
            else nickNameCheck = true

            if(weightCheck && bikeWeightCheck && nickNameCheck) modify()
            else{
                AlertDialog.Builder(this@ProfileModifyActivity)
                    .setTitle("입력 오류!")
                    .setMessage("\n입력 사항에 문제가 있습니다.\n아래와 같이 입력이 되었는지 확인해주세요.\n(닉네임 한글로 2자-14자\n 자전거 무게 6 이상, 몸무게 30이상)")
                    .setPositiveButton("확인") { _, _ ->}
                    .setCancelable(false)
                    .create()
                    .show()
            }

        }


    }

    private fun nameCheck(name : String) : Boolean{
        val pwPattern = "^[가-힣]{2,14}$" // 한글로만 2~14자
        return Pattern.matches(pwPattern, name)
    }

    // 닉네임 중복 체크
    private fun nickNameCheck(nickName : String){
        val retrofitAPI = RetrofitClient.getSimpleInstance().create(LoginService::class.java)

        retrofitAPI.getNickNameCode(nickName).enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        if(response.body()!!){
                            nickNameCheck = false
                            AlertDialog.Builder(this@ProfileModifyActivity)
                                .setTitle("닉네임 중복 오류")
                                .setMessage("\n이미 사용하고 있는 닉네임 입니다.")
                                .setPositiveButton("확인") { _, _ -> }
                                .setCancelable(false)
                                .create()
                                .show()
                        }
                        else nickNameCheck = true
                    }
                    else{
                        Toast.makeText(this@ProfileModifyActivity,"네트워크 접속 오류 발생" ,Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this@ProfileModifyActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@ProfileModifyActivity,"네트워크 접속 오류 발생",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAllSi(){
        val retrofitAPI = RetrofitClient.getInstance().create(RegionService::class.java)

        retrofitAPI.getAllSi().enqueue(object : Callback<SiData> {
            override fun onResponse(call: Call<SiData>, response: Response<SiData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        siList = response.body()!!
                        var siIndex = 0
                        val list : MutableList<String> = mutableListOf()
                        for( index in siList.indices){
                            list.add(siList[index].name)
                            if(userProfile.getString("si",null) == siList[index].name) siIndex = index
                        }
                        val adapter = ArrayAdapter<String>(this@ProfileModifyActivity, R.layout.simple_list_item_1, list)
                        binding.spSi.adapter = adapter

                        if(siList[0].name.isEmpty()){
                            AlertDialog.Builder(this@ProfileModifyActivity)
                                .setTitle("네트워크 접속 오류")
                                .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                                .setPositiveButton("확인") { _, _ -> finish()}
                                .setCancelable(false)
                                .create()
                                .show()
                        }
                        else{
                            binding.spSi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    getGun(siList[position].si_code.toString())
                                    si = siList[position].name
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }

                            }
                            binding.spSi.setSelection(siIndex)
                        }


                    }
                    else{
                        AlertDialog.Builder(this@ProfileModifyActivity)
                            .setTitle("네트워크 접속 오류")
                            .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                            .setPositiveButton("확인") { _, _ -> finish()}
                            .setCancelable(false)
                            .create()
                            .show()
                    }
                }
                else{
                    AlertDialog.Builder(this@ProfileModifyActivity)
                        .setTitle("네트워크 접속 오류")
                        .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                        .setPositiveButton("확인") { _, _ -> finish()}
                        .setCancelable(false)
                        .create()
                        .show()
                }
            }
            override fun onFailure(call: Call<SiData>, t: Throwable) {
                AlertDialog.Builder(this@ProfileModifyActivity)
                    .setTitle("네트워크 접속 오류")
                    .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                    .setPositiveButton("확인") { _, _ -> finish()}
                    .setCancelable(false)
                    .create()
                    .show()
            }
        })
    }

    private fun getGun(si_code : String){
        val retrofitAPI = RetrofitClient.getInstance().create(RegionService::class.java)

        retrofitAPI.getSelectedGun(si_code).enqueue(object : Callback<GunData> {
            override fun onResponse(call: Call<GunData>, response: Response<GunData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        gunList = response.body()!!
                        val list2 : MutableList<String> = mutableListOf()
                        var gunIndex = 0
                        for( index in gunList.indices){
                            list2.add(gunList[index].name)
                            if(userProfile.getString("gun",null)==gunList[index].name) gunIndex = index
                        }
                        val adapter = ArrayAdapter<String>(this@ProfileModifyActivity, R.layout.simple_list_item_1, list2)
                        binding.spGun.adapter = adapter

                        binding.spGun.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                getDong(gunList[position].si_code.toString(), gunList[position].gun_code.toString())
                                gun = gunList[position].name
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }

                        }
                        if(!gunSelected){
                            binding.spGun.setSelection(gunIndex)
                            gunSelected = true
                        }

                    }
                    else{
                        AlertDialog.Builder(this@ProfileModifyActivity)
                            .setTitle("네트워크 접속 오류")
                            .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                            .setPositiveButton("확인") { _, _ -> finish()}
                            .setCancelable(false)
                            .create()
                            .show()
                    }
                }
                else{
                    AlertDialog.Builder(this@ProfileModifyActivity)
                        .setTitle("네트워크 접속 오류")
                        .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                        .setPositiveButton("확인") { _, _ -> finish()}
                        .setCancelable(false)
                        .create()
                        .show()
                }
            }
            override fun onFailure(call: Call<GunData>, t: Throwable) {
                AlertDialog.Builder(this@ProfileModifyActivity)
                    .setTitle("네트워크 접속 오류")
                    .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                    .setPositiveButton("확인") { _, _ -> finish()}
                    .setCancelable(false)
                    .create()
                    .show()
            }
        })
    }

    private fun getDong(si_code : String, gun_code : String){
        val retrofitAPI = RetrofitClient.getInstance().create(RegionService::class.java)

        retrofitAPI.getSelectedDong(si_code, gun_code).enqueue(object : Callback<DongData> {
            override fun onResponse(call: Call<DongData>, response: Response<DongData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        dongList = response.body()!!
                        val list2 : MutableList<String> = mutableListOf()
                        var dongIndex = 0
                        for( index in dongList.indices){
                            list2.add(dongList[index].name)
                            if(dongList[index].name==userProfile.getString("dong",null)) dongIndex = index
                        }
                        val adapter = ArrayAdapter<String>(this@ProfileModifyActivity, R.layout.simple_list_item_1, list2)
                        binding.spDong.adapter = adapter

                        binding.spDong.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                dong = dongList[position].name
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }
                        }

                        if(!dongSelected){
                            binding.spDong.setSelection(dongIndex)
                            dongSelected = true
                        }

                    }
                    else{
                        AlertDialog.Builder(this@ProfileModifyActivity)
                            .setTitle("네트워크 접속 오류")
                            .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                            .setPositiveButton("확인") { _, _ -> finish()}
                            .setCancelable(false)
                            .create()
                            .show()
                    }
                }
                else{
                    AlertDialog.Builder(this@ProfileModifyActivity)
                        .setTitle("네트워크 접속 오류")
                        .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                        .setPositiveButton("확인") { _, _ -> finish()}
                        .setCancelable(false)
                        .create()
                        .show()
                }
            }
            override fun onFailure(call: Call<DongData>, t: Throwable) {
                AlertDialog.Builder(this@ProfileModifyActivity)
                    .setTitle("네트워크 접속 오류")
                    .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                    .setPositiveButton("확인") { _, _ -> finish()}
                    .setCancelable(false)
                    .create()
                    .show()
            }
        })
    }

    private fun modify(){
        val retrofitAPI = RetrofitClient.getInstance().create(LoginService::class.java)

//        Toast.makeText(this@ProfileModifyActivity, "새 open : " + binding.sOpen.isChecked.toString() + "\n새 닉네임 : " +
//                binding.etModifyNickName.text + "\n새 몸무게 : " + binding.etModifyWeight.text + "\n새 자전거 무게 : "
//                + binding.etModifyBikeWeight.text + "\n시 : " + si + " 군 : "+ gun + " 동" + dong, Toast.LENGTH_LONG).show()

        val modifyUserProfileData = ModifyUserProfileData(
       userProfile.getString("email", null)!!, binding.etModifyNickName.text.toString(),
            userProfile.getString("image_path",null)!!, binding.sOpen.isChecked.toString(),
            si, gun, dong, binding.etModifyWeight.text.toString(), binding.etModifyBikeWeight.text.toString(),
        userProfile.getString("token",null)!!)

        retrofitAPI.modifyMyProfile(modifyUserProfileData).enqueue(object : Callback<ModifyUserProfileData> {
            override fun onResponse(call: Call<ModifyUserProfileData>, response: Response<ModifyUserProfileData>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        response.let {

                            if(it.code() == 200){
                                val userProfileEdit = userProfile.edit()
                                userProfileEdit.putString("email",it.body()!!.email)
                                userProfileEdit.putString("nickname",it.body()!!.nickname)
                                userProfileEdit.putString("image_path",it.body()!!.image_path)
                                userProfileEdit.putBoolean("open",it.body()!!.open.toBoolean())
                                userProfileEdit.putString("si",it.body()!!.si)
                                userProfileEdit.putString("gun",it.body()!!.gun)
                                userProfileEdit.putString("dong",it.body()!!.dong)
                                userProfileEdit.putInt("weight",it.body()!!.weight.toInt())
                                userProfileEdit.putInt("cycle_weight",it.body()!!.cycle_weight.toInt())
                                userProfileEdit.apply()
                                updatePhoto()

                                AlertDialog.Builder(this@ProfileModifyActivity)
                                    .setTitle("수정 완료")
                                    .setMessage("\n수정이 완료되었습니다!")
                                    .setPositiveButton("확인") { _, _ -> finish()}
                                    .setCancelable(false)
                                    .create()
                                    .show()

                            }else{//정보가 틀릴 때
                                Toast.makeText(this@ProfileModifyActivity, "아이디 혹은 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                            }

                        }

                    }
                    else{
                        AlertDialog.Builder(this@ProfileModifyActivity)
                            .setTitle("네트워크 접속 오류")
                            .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                            .setPositiveButton("확인") { _, _ -> finish()}
                            .setCancelable(false)
                            .create()
                            .show()
                    }
                }
                else{
                    AlertDialog.Builder(this@ProfileModifyActivity)
                        .setTitle("네트워크 접속 오류")
                        .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                        .setPositiveButton("확인") { _, _ -> finish()}
                        .setCancelable(false)
                        .create()
                        .show()
                }
            }
            override fun onFailure(call: Call<ModifyUserProfileData>, t: Throwable) {
                AlertDialog.Builder(this@ProfileModifyActivity)
                    .setTitle("네트워크 접속 오류")
                    .setMessage("\n네트워크 접속 오류가 발생하였습니다.\n네트워크를 확인 후 다시 시도해주세요")
                    .setPositiveButton("확인") { _, _ -> finish()}
                    .setCancelable(false)
                    .create()
                    .show()
            }
        })


    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder(this@ProfileModifyActivity)
                .setTitle("수정 취소")
                .setMessage("\n수정을 그만두시겠습니까?")
                .setPositiveButton("네") { _, _ -> finish()}
                .setNegativeButton("아니오"){_,_ -> }
                .setCancelable(false)
                .create()
                .show()
            return true
        }

        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    navigateGallery()
                else
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                //
            }
        }
    }

    private fun navigateGallery() {
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        val intent = Intent(Intent.ACTION_PICK)
        // 가져올 컨텐츠들 중에서 Image 만을 가져온다.
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes)

        // 갤러리에서 이미지를 선택한 후, 프로필 이미지뷰를 수정하기 위해 갤러리에서 수행한 값을 받아오는 startActivityForeResult를 사용한다.
        startActivityForResult(intent, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 예외처리
        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode) {
            // 2000: 이미지 컨텐츠를 가져오는 액티비티를 수행한 후 실행되는 Activity 일 때만 수행하기 위해서
            2000 -> {
                val selectedImageUri = data?.data
                if (selectedImageUri != null) {
                    binding.ciProfileImage.setImageURI(selectedImageUri)

                    val proj = arrayOf(MediaStore.Images.Media.DATA)
                    val cursorLoader = CursorLoader(this, selectedImageUri, proj, null, null, null)
                    val cursor: Cursor = cursorLoader.loadInBackground()

                    val columnIndex: Int =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor.moveToFirst()
                    imageurl = cursor.getString(columnIndex)
                    cursor.close()
                } else {
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("프로필 이미지를 바꾸기 위해서는 갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }

    private fun updatePhoto() {
        if (imageurl != null) {
            val file = File(imageurl)

            var formatOfFile = imageurl!!.substring(imageurl!!.length-4, imageurl!!.length)
            formatOfFile = formatOfFile.replace(".","")

            if(formatOfFile=="jpeg" || formatOfFile=="jpg" || formatOfFile=="png"){

                // 직접 뽑아서 확장자를 확인해본다.
                val requestBody: RequestBody = when(formatOfFile){
                    "png" -> RequestBody.create(MediaType.parse("image/png"), file)
                    else -> RequestBody.create(MediaType.parse("image/jpeg"), file)
                }

                val fileToUpload: MultipartBody.Part = MultipartBody.Part.createFormData("imageFile", file.name, requestBody)
                modifyMyProfileImage(fileToUpload)

            }
            else{
                Toast.makeText(this@ProfileModifyActivity, "JPG나 PNG 파일만 서버에 업로드 가능합니다.", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun modifyMyProfileImage(fileToUpload: MultipartBody.Part){
        val retrofitAPI = RetrofitClient.getSimpleInstance().create(FileService::class.java)

        retrofitAPI.postImageFile(
            userProfile.getString("token",null)!!,
            fileToUpload).enqueue(object :
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        getMyProfileImage()
                    }
                }
                else{

                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

            }
        })
    }

    private fun getMyProfileImage(){
        val retrofitAPI = RetrofitClient.getInstance().create(FileService::class.java)

        retrofitAPI.downloadImage(userProfile.getString("id",null)!!).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful && response.body() != null){
                    val byteValue = response.body()!!.byteStream()
                    val bitmap = BitmapFactory.decodeStream(byteValue)
                    val bitmapToString = ProfileSet.bitmapToString(bitmap)
                    userProfile.edit().putString("profileImage", bitmapToString).apply()

                    binding.ciProfileImage.setImageBitmap(ProfileSet.stringToBitmap(userProfile.getString("profileImage","null")))

                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })
    }


}