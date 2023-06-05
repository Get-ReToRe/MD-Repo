package com.capstone.getretore.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.getretore.R
import com.capstone.getretore.ui.DetailActivity
import com.capstone.getretore.user.PlaceData

class PlaceAdapter(
    private val context: Context,
    private val placeList: ArrayList<PlaceData>
) : RecyclerView.Adapter<PlaceAdapter.MyViewHolder>() {

    companion object {
        const val EXTRA_PLACE_DATA = "extra_place_data"
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvPrice = view.findViewById<TextView>(R.id.tv_price)
        val ivImage = view.findViewById<ImageView>(R.id.iv_image)
        val cvMain = view.findViewById<CardView>(R.id.cv_main)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.card_travel, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val place = placeList[position]
        holder.tvName.text = placeList.get(position).Place_Name
        holder.tvPrice.text = placeList.get(position).Price.toString()
        Glide.with(context)
            .load(place.Image)
            .fitCenter()
            .into(holder.ivImage)
        holder.cvMain.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_PLACE_DATA, place)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = placeList.size

    fun setData(data: ArrayList<PlaceData>) {
        placeList.clear()
        placeList.addAll(data)
        notifyDataSetChanged()
    }
}
