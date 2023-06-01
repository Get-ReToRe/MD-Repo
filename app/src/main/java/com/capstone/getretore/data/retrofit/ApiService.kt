package com.capstone.getretore.data.retrofit

import com.capstone.getretore.data.response.ListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("places")
    suspend fun getListWisata(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ListResponse>

}