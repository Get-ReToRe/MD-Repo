package com.capstone.getretore.viewModel

import android.service.autofill.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.getretore.data.response.DataItem
import com.capstone.getretore.data.response.ListResponse
import com.capstone.getretore.service.WisataRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainViewModel (private val repo: WisataRepository) : ViewModel() {

    val list: LiveData<PagingData<DataItem>> = repo.getList()
    val getListStories: LiveData<PagingData<DataItem>> =
        repo.getList().cachedIn(viewModelScope)

    fun getStory(token: String): LiveData<PagingData<DataItem>> {
        return repo.getList().cachedIn(viewModelScope)
    }

    fun getSession(): LiveData<com.capstone.getretore.user.UserData> {
        return repo.getSession()
    }
}