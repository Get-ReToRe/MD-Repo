package com.capstone.getretore.data.retrofit


import com.capstone.getretore.data.response.PlaceResponseItem
import com.capstone.getretore.user.BudgetPredictData
import com.capstone.getretore.user.PlaceData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("places")
    fun getPlaces(): Call<ArrayList<PlaceData>>

    @GET("places")
    fun getDetail(): Call<PlaceResponseItem>

    @Multipart
    @POST("predict")
    fun postImage(
        @Part file: MultipartBody.Part,
    ): Call<ArrayList<PlaceData>>

    @POST("recommendation")
    fun getRecommendation(
        @Body requestBody: RequestBody
    ): Call<ArrayList<BudgetPredictData>>

}