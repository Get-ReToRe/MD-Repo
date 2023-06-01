package com.capstone.getretore.data.response

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class ListResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

@Entity(tableName = "places")
data class DataItem(

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
)
