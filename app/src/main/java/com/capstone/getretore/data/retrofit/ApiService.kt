package com.capstone.getretore.data.retrofit

import com.capstone.getretore.user.PlaceData
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("places")
    fun getPlaces(): Call<ArrayList<PlaceData>>

}