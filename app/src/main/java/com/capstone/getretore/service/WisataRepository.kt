package com.capstone.getretore.service

import android.service.autofill.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.*
import com.capstone.getretore.data.response.DataItem
import com.capstone.getretore.data.response.ListResponse
import com.capstone.getretore.data.response.WisataPaggingSource
import com.capstone.getretore.data.retrofit.ApiConfig
import com.capstone.getretore.data.retrofit.ApiService
import com.capstone.getretore.user.UserPreferences

class WisataRepository private constructor(private val pref: UserPreferences, private val apiService: ApiService) {

    private val _list = MutableLiveData<ListResponse>()
//    val list: LiveData<ListResponse> = _list

    companion object {
        private const val TAG = "WisataRepository"

        @Volatile
        private var instance: WisataRepository? = null
        fun getInstance(
            preferences: UserPreferences,
            apiService: ApiService
        ): WisataRepository =
            instance ?: synchronized(this) {
                instance ?: WisataRepository(preferences, apiService)
            }.also { instance = it }
    }

    fun getList(): LiveData<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                WisataPaggingSource(pref, apiService)
            }
        ).liveData
    }

    fun getSession(): LiveData<com.capstone.getretore.user.UserData> {
        return pref.getSession().asLiveData()
    }

    suspend fun saveSession(session: com.capstone.getretore.user.UserData) {
        pref.saveSession(session)
    }
}