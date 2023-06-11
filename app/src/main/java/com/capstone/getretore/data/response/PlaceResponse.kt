package com.capstone.getretore.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PlaceResponse(

	@field:SerializedName("PlaceResponse")
	val placeResponse: List<PlaceResponseItem>
)

@Parcelize
data class PlaceResponseItem(

	@field:SerializedName("Time_Minutes")
	val timeMinutes: Int,

	@field:SerializedName("Description")
	val description: String,

	@field:SerializedName("Category")
	val category: String,

	@field:SerializedName("Place_Id")
	val placeId: Int,

	@field:SerializedName("Place_Name")
	val placeName: String,

	@field:SerializedName("Price")
	val price: Int,

	@field:SerializedName("Coordinate")
	val coordinate: String,

	@field:SerializedName("Rating")
	val rating: Int,

	@field:SerializedName("Long")
	val long: Int,

	@field:SerializedName("City")
	val city: String,

	@field:SerializedName("Image")
	val image: String,

	@field:SerializedName("Lat")
	val lat: Int
): Parcelable
