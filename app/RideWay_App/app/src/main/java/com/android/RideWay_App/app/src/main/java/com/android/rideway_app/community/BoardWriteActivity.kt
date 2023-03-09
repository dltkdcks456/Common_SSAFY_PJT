package com.android.rideway_app.community

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.rideway_app.databinding.ActivityBoardWriteBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.community.BoardInsertData
import com.android.rideway_app.retrofit.community.BoardService
import jp.wasabeef.richeditor.RichEditor
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class BoardWriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardWriteBinding
    private lateinit var mEditor : RichEditor
    private lateinit var mPreview: TextView
    private lateinit var userInfo: SharedPreferences

    private val imgMap = mutableMapOf<String,String>()
    private var imgCnt = 0

    private val images  = arrayListOf<MultipartBody.Part>()

    private val DEFAULT_GALLERY_REQUEST_CODE = 5959

//    private var image : MultipartBody.Part? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userInfo = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        mEditor = binding.editor
        mEditor.setEditorHeight(300)
        mEditor.setEditorFontSize(22)
        mEditor.setEditorBackgroundColor(Color.WHITE)
        mEditor.setBackgroundColor(Color.WHITE)

        mEditor.setPadding(10, 10, 10, 10)
        mEditor.setPlaceholder("Insert text here...")
        mPreview = binding.preview
        mEditor.setOnTextChangeListener { text -> mPreview.setText(text) }
        setAllClickEventOnEditor()
        checkSelfPermission()
        binding.btnInsert.setOnClickListener {
            insertBoard()
        }
    }

    private fun insertBoard(){
        if(binding.title.text.isNullOrBlank()){
            Toast.makeText(this@BoardWriteActivity,"제목을 입력해주세요",Toast.LENGTH_SHORT).show()
            binding.title.requestFocus()
            return
        }
        var userId = userInfo.getInt("user_id" , 0)
        var token = userInfo.getString("token" , null)
        var title = binding.title.text.toString()
        var content = binding.preview.text.toString()
        var boardCode = intent.getStringExtra("board_code")

        var map = imgMap.toString().replace("=",":")
        val requestBody = hashMapOf<String,RequestBody>()
        requestBody["user_id"] = RequestBody.create(MediaType.parse("text/plain"),userId.toString())
        requestBody["token"] = RequestBody.create(MediaType.parse("text/plain"),token)
        requestBody["title"] = RequestBody.create(MediaType.parse("text/plain"),title)
        requestBody["content"] = RequestBody.create(MediaType.parse("text/plain"),content)
        requestBody["board_code"] = RequestBody.create(MediaType.parse("text/plain"),boardCode)
        requestBody["imgMap"] = RequestBody.create(MediaType.parse("application/json"),map)

        val retrofitAPI = RetrofitClient.getInstance().create(BoardService::class.java)

        retrofitAPI.insertBoard(requestBody,images).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                println(response)
                if(response.isSuccessful){
                    Toast.makeText(this@BoardWriteActivity,"게시글 등록 성공",Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@BoardWriteActivity,"게시글 등록 실패",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }


    private fun setAllClickEventOnEditor(){

        binding.actionUndo.setOnClickListener{ mEditor.undo() }

        binding.actionRedo.setOnClickListener{ mEditor.redo() }

        binding.actionBold.setOnClickListener{ mEditor.setBold() }

        binding.actionItalic.setOnClickListener{ mEditor.setItalic() }

        binding.actionSubscript.setOnClickListener{ mEditor.setSubscript() }

        binding.actionSuperscript.setOnClickListener{ mEditor.setSuperscript() }

        binding.actionStrikethrough.setOnClickListener{ mEditor.setStrikeThrough() }

        binding.actionUnderline.setOnClickListener{ mEditor.setUnderline() }

        binding.actionHeading1.setOnClickListener{ mEditor.setHeading(1) }

        binding.actionHeading2.setOnClickListener{ mEditor.setHeading(2) }

        binding.actionHeading3.setOnClickListener{ mEditor.setHeading(3) }

        binding.actionHeading4.setOnClickListener{ mEditor.setHeading(4) }

        binding.actionHeading5.setOnClickListener{ mEditor.setHeading(5) }

        binding.actionHeading6.setOnClickListener{ mEditor.setHeading(6) }


        binding.actionTxtColor.setOnClickListener(object : OnClickListener {
            private var isChanged = false
            override fun onClick(p0: View?) {
                mEditor.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })

        binding.actionBgColor.setOnClickListener(object : OnClickListener {
            private var isChanged = false
            override fun onClick(v: View?) {
                mEditor.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
                isChanged = !isChanged
            }
        })

        binding.actionIndent.setOnClickListener{ mEditor.setIndent() }

        binding.actionOutdent.setOnClickListener{ mEditor.setOutdent() }

        binding.actionAlignLeft.setOnClickListener{ mEditor.setAlignLeft() }

        binding.actionAlignCenter.setOnClickListener { mEditor.setAlignCenter() }

        binding.actionAlignRight.setOnClickListener { mEditor.setAlignRight() }

        binding.actionBlockquote.setOnClickListener { mEditor.setBlockquote() }

        binding.actionInsertBullets.setOnClickListener { mEditor.setBullets() }

        binding.actionInsertNumbers.setOnClickListener { mEditor.setNumbers() }

        binding.actionInsertImage.setOnClickListener { selectGallery() }

        binding.actionInsertYoutube.setOnClickListener { mEditor.insertYoutubeVideo("https://www.youtube.com/embed/pS5peqApgUA") }

        binding.actionInsertAudio.setOnClickListener { mEditor.insertAudio("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_5MG.mp3") }

        binding.actionInsertVideo.setOnClickListener {
            mEditor.insertVideo(
                "https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_10MB.mp4",
                360
            )
        }

        binding.actionInsertLink.setOnClickListener {
            mEditor.insertLink(
                "https://github.com/wasabeef",
                "wasabeef"
            )
        }
        binding.actionInsertCheckbox.setOnClickListener { mEditor.insertTodo() }

    }


    // 이미지 실제 경로 반환

    private fun getPath(path: Uri, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c = context.contentResolver.query(path, proj, null, null, null)
        var index = c!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c.moveToFirst()
        var result = c.getString(index)

        return result
    }

    // 갤러리를 부르는 메서드
    private fun selectGallery(){
        startDefaultGalleryApp()
    }

    private fun startDefaultGalleryApp() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, DEFAULT_GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) {
            return
        }

        when (requestCode) {
            DEFAULT_GALLERY_REQUEST_CODE -> {
                data?:return
                val uri = data.data as Uri
                // 이미지 URI를 가지고 하고 싶은거 하면 된다.
                val uriString = uri.toString()
                mEditor.insertImage(
                    uri.toString(),
                    "dachshund", 320
                )
                //리스트에 이미지 추가
                var file = File(getPath(uri,this))
                val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
                val body = MultipartBody.Part.createFormData("images", file.name, requestFile)
                images.add(body)
//                image
                imgMap["\"" + uri.toString() + "\""] = "\"" + imgCnt.toString() + "\""
                imgCnt++
            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }



    //권한 허용

    //권한에 대한 응답이 있을때 작동하는 함수
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //권한을 허용 했을 경우
        if (requestCode == 1) {
            val length = permissions.size
            for (i in 0 until length) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 동의
                    Log.d("MainActivity", "권한 허용 : " + permissions[i])
                }
            }
        }
    }

    fun checkSelfPermission() {
        var temp = ""

        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " "
        }

        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " "
        }
        if (TextUtils.isEmpty(temp) == false) {
            // 권한 요청
            ActivityCompat.requestPermissions(
                this,
                temp.trim { it <= ' ' }.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray(),
                1)
        } else {
            // 모두 허용 상태
            Toast.makeText(this, "권한을 모두 허용", Toast.LENGTH_SHORT).show()
        }
    }

}