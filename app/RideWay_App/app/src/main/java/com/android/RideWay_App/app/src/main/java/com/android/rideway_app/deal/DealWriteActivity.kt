package com.android.rideway_app.deal

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.rideway_app.databinding.ActivityDealWriteBinding
import com.android.rideway_app.retrofit.RetrofitClient
import com.android.rideway_app.retrofit.deal.DealService
import jp.wasabeef.richeditor.RichEditor
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import retrofit2.http.Multipart
import java.io.File

class DealWriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDealWriteBinding
    private lateinit var userInfo : SharedPreferences
    private lateinit var mEditor : RichEditor

    private val uriList = mutableListOf<Uri>()
    private val REQUEST_CODE = 5959
    private val adapter = DealImagePreviewAdapter(uriList)
    private val retrofitAPI = RetrofitClient.getInstance().create(DealService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDealWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userInfo = getSharedPreferences("UserProfile", Activity.MODE_PRIVATE)

        binding.previewRecyclerView.adapter = this.adapter
        binding.previewRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true)


        //editor setting
        mEditor = binding.dealEditor
        mEditor.setEditorHeight(300)
        mEditor.setEditorFontSize(22)
        mEditor.setEditorBackgroundColor(Color.WHITE)
        mEditor.setBackgroundColor(Color.WHITE)

        mEditor.setPadding(10, 10, 10, 10)
        mEditor.setPlaceholder("Insert text here...")
        mEditor.setOnTextChangeListener { text -> binding.preview.setText(text) }


        binding.btnImageUpload.setOnClickListener {
            if(uriList.size >= 5){
                Toast.makeText(this@DealWriteActivity,"이미지는 최대 5장 선택 가능합니다.",Toast.LENGTH_SHORT).show()
            }else {
                val intent = Intent(Intent.ACTION_PICK)
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE)
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        }

        binding.btnInsert.setOnClickListener {
            insertDeal()
        }

        setEditor()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE){
            if(data == null){ //이미지를 선택하지 않은 경우
                Toast.makeText(this@DealWriteActivity,"이미지를 선택해주세요.",Toast.LENGTH_SHORT).show()
            }else{
                if(data.clipData == null){ // 이미지를 하나만 선택한 경우
                    val uri = data.data!!
                    uriList.add(uri)
                }else{ // 이미지를 여러장 선택한 경우
                    val clipData = data.clipData

                    if(uriList.size + clipData!!.itemCount > 5){ //선택된 이미지가 총 5장 이상이 될 경우
                        Toast.makeText(this@DealWriteActivity,"이미지가 5장을 초과합니다.",Toast.LENGTH_SHORT).show()
                    }else{
                        for(i in 0 until clipData.itemCount){
                            val uri = clipData.getItemAt(i).uri
                            uriList.add(uri)
                        }
                    }
                    
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun getPath(path: Uri, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c = context.contentResolver.query(path, proj, null, null, null)
        var index = c!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c.moveToFirst()
        return c.getString(index)
    }

    private fun insertDeal(){
        val userId = userInfo.getInt("user_id" , 0).toString()
        val token = userInfo.getString("token",null);
        val title = binding.etTitle.text.toString()
        val content = binding.preview.text.toString()
        val name = binding.etProduct.text.toString()
        val price = binding.etPrice.text.toString()
        val images = arrayListOf<MultipartBody.Part>()

        for(i in 0 until uriList.size){
            var img = File(getPath(uriList[i],this))
            val requestFile = RequestBody.create(MediaType.parse("image/*"), img)
            val body = MultipartBody.Part.createFormData("images", img.name, requestFile)
            images.add(body)
        }

        val requestBody = hashMapOf<String,RequestBody>()
        requestBody["user_id"] = RequestBody.create(MediaType.parse("text/plain"),userId)
        requestBody["token"] = RequestBody.create(MediaType.parse("text/plain"),token)
        requestBody["title"] = RequestBody.create(MediaType.parse("text/plain"),title)
        requestBody["content"] = RequestBody.create(MediaType.parse("text/plain"),content)
        requestBody["name"] = RequestBody.create(MediaType.parse("text/plain"),name)
        requestBody["price"] = RequestBody.create(MediaType.parse("text/plain"),price)

        retrofitAPI.insertDeal(requestBody,images).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    Toast.makeText(this@DealWriteActivity, "등록 완료", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    println(response)
                    Toast.makeText(this@DealWriteActivity,"등록 실패",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@DealWriteActivity,"네트워크 오류",Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun setEditor(){
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


        binding.actionTxtColor.setOnClickListener(object : View.OnClickListener {
            private var isChanged = false
            override fun onClick(p0: View?) {
                mEditor.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })

        binding.actionBgColor.setOnClickListener(object : View.OnClickListener {
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

        binding.actionInsertCheckbox.setOnClickListener { mEditor.insertTodo() }

    }
}