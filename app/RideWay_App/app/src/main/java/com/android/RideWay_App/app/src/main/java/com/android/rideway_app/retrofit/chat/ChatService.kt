package com.android.rideway_app.retrofit.chat

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ChatService {
    //로그인
    @POST("chat/roomForDeal")
    fun createDealChatRoom(@Body createChatRoomData: CreateChatRoomData) : Call<ChatRoomResponseData>

    @GET("chat/rooms/user/nameAndId")
    fun getMyChatroom(@Header("token") token : String) : Call<ChatRoomListResponseData>

    @GET("chat/callChatting")
    fun getChatHistory(@Query("roomId") roomId : String) : Call<ChatHistory>

    @GET("chat/getRoomUsers")
    fun getChatUsers(@Query("roomId") roomId : String) : Call<ChatRoomUsers>

    @POST("chat/enterUserRoom")
    fun enterCommunityChat(@Body chatCommunityData: ChatCommunityData) : Call<ChatCommunityEnterResponseData>

    @HTTP(method = "DELETE", path = "chat/roomOut", hasBody = true)
    fun deleteChat(@Body deleteChatData: DeleteChatData) : Call<ResponseBody>
}