package com.capstone.getretore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.capstone.getretore.R
import com.capstone.getretore.adapter.PlaceAdapter
import com.capstone.getretore.databinding.ActivityDetailBinding
import com.capstone.getretore.user.PlaceData

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_detail)

        val place = intent.getParcelableExtra<PlaceData>(PlaceAdapter.EXTRA_PLACE_DATA) as PlaceData
        binding.apply {
            tvName.text = place.Place_Name
            tvPrice.text = place.Price.toString()
            tvDescription.text = place.Description
            Glide.with(this@DetailActivity)
                .load(place.Image)
                .fitCenter()
                .into(ivPlace)
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}