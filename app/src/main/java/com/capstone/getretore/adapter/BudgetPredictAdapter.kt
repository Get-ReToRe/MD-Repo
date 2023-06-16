package com.capstone.getretore.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.getretore.R
import com.capstone.getretore.data.response.PlaceResponseItem
import com.capstone.getretore.databinding.CardTravelBinding
import com.capstone.getretore.ui.DetailActivity
import com.capstone.getretore.ui.DetailActivity.Companion.EXTRA_DATA
import com.capstone.getretore.user.BudgetPredictData
import com.capstone.getretore.user.PlaceData

class BudgetPredictAdapter(
    private val context: Context,
    private val placeList: ArrayList<BudgetPredictData>
) : RecyclerView.Adapter<BudgetPredictAdapter.MyViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        const val EXTRA_PLACE_DATA = "extra_place_data"
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvPrice = view.findViewById<TextView>(R.id.tv_price)
        val ivImage = view.findViewById<ImageView>(R.id.iv_image)
        val tvCity = view.findViewById<TextView>(R.id.tv_city)
        val rvMain = view.findViewById<RecyclerView>(R.id.rv_main)
        val cvMain = view.findViewById<CardView>(R.id.cv_main)
    }

    class ListViewHolder(private val binding: CardTravelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(place: PlaceResponseItem) {
            binding.apply {
                tvName.text = place.placeName
                Glide.with(itemView.context)
                    .load(place.image)
                    .fitCenter()
                    .into(ivImage)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(EXTRA_DATA, place)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(ivImage, "place"),
                            Pair(tvName, "name"),
                            Pair(tvCity, "city"),
                            Pair(tvPrice, "price")
                        )
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.card_travel, parent, false)
        val binding = CardTravelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val place = placeList[position]
//        if (place != null) {
//            holder.bind(place)
//        }

        holder.tvName.text = placeList.get(position).Place_Name
        holder.tvCity.text = placeList.get(position).City + "\n( ${placeList.get(position).Distance} KM) "
        holder.tvPrice.text = "Rp." + placeList.get(position).Price.toString()
        Glide.with(context)
            .load(place.Image)
            .fitCenter()
            .into(holder.ivImage)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_PLACE_DATA, place)
            context.startActivity(intent)
            onItemClickCallback?.onItemClicked(place)
        }
    }

    override fun getItemCount(): Int = placeList.size

    fun setData(data: ArrayList<BudgetPredictData>) {
        placeList.clear()
        placeList.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: BudgetPredictData)
    }
}
