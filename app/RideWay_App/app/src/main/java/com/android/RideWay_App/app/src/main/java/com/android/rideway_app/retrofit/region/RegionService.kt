package com.android.rideway_app.retrofit.region

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RegionService {
    @GET("region/si")
    fun getAllSi() : Call<SiData>

    @GET("region/si/gun")
    fun getSelectedGun( @Query("si_code") si_code :String ) : Call<GunData>

    @GET("region/si/gun/dong")
    fun getSelectedDong( @Query("si_code") si_code :String, @Query("gun_code") gun_code : String) : Call<DongData>
}