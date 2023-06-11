package com.capstone.getretore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.capstone.getretore.R
import com.capstone.getretore.adapter.PlaceAdapter
import com.capstone.getretore.data.response.PlaceResponse
import com.capstone.getretore.data.response.PlaceResponseItem
import com.capstone.getretore.data.retrofit.ApiConfig
import com.capstone.getretore.databinding.ActivityDetailBinding
import com.capstone.getretore.ui.viewModel.DetailViewModel
import com.capstone.getretore.user.PlaceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_detail)

//        setDetail()

        detailViewModel.detail.observe(this, {
            setDetail(it)
        })

        detailViewModel.detailPlace()
    }

    private fun setDetail(data: PlaceData) {
        val name = intent.getStringExtra("NAME")
        val price = intent.getStringExtra("PRICE")
        val description = intent.getStringExtra("DESCRIPTION")
        val image = intent.getStringExtra("IMAGE")

//        val bundle: Bundle? = intent.extras
//        val tvName: TextView = findViewById(R.id.tv_name)
//        val tvPrice: TextView = findViewById(R.id.tv_price)
//        val tvDescription: TextView = findViewById(R.id.tv_description)
//        val ivPlace: ImageView = findViewById(R.id.iv_place)

        Glide.with(this)
            .load(image)
            .into(binding.ivPlace)
        binding.tvName.text = name
        binding.tvPrice.text = price
        binding.tvDescription.text = description

//        Glide
//            .with(this)
//            .load(data.Image)
//            .into(binding.ivPlace);
//        binding.tvName.text = data.Place_Name
//        binding.tvPrice.text = "Rp." + data.Price.toString()
//        binding.tvDescription.text = data.Description
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}