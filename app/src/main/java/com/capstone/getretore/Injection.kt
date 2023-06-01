package com.capstone.getretore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.capstone.getretore.data.retrofit.ApiConfig
import com.capstone.getretore.service.WisataRepository
import com.capstone.getretore.user.UserPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")

object Injection {
    fun provideRepository(context: Context): WisataRepository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return WisataRepository.getInstance(preferences, apiService)
    }
}