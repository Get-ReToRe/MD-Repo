package com.capstone.getretore.ui.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.getretore.data.retrofit.ApiConfig
import com.capstone.getretore.user.PlaceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val _detailPlace = MutableLiveData<PlaceData>()
    val detail: LiveData<PlaceData> = _detailPlace

    fun detailPlace(){
        val client = ApiConfig.getApiService().getDetail()
        client.enqueue(object : Callback<PlaceData> {
            override fun onResponse(
                call: Call<PlaceData>,
                response: Response<PlaceData>
            ) {
                if (response.isSuccessful) {
                    _detailPlace.value = response.body()
                } else {

                }
            }

            override fun onFailure(call: Call<PlaceData>, t: Throwable) {

            }

        })
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<PlaceData>) {

}
