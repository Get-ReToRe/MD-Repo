package com.capstone.getretore.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceData(
    val Place_Id: Int,
    val Place_Name: String,
    val Price: Int,
    val Image: String,
    val Description: String,
    val City: String
) :Parcelable