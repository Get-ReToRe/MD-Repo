package com.capstone.getretore.adapter

import android.app.Activity
import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_DATA
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.fitCenter
import com.bumptech.glide.request.RequestOptions
import com.capstone.getretore.R
import com.capstone.getretore.data.response.DataItem
import com.capstone.getretore.databinding.CardTravelBinding
import com.capstone.getretore.ui.DetailActivity

class ListAdapter : PagingDataAdapter<DataItem, ListAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            CardTravelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

//    class MyViewHolder(private val binding: CardTravelBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(data: DataItem) {
//            binding.tvName.text = data.placeName
//            binding.tvPrice.text = data.price.toString()
//            Glide.with(itemView.context)
//                .load(data.image)
//                .fitCenter()
//                .apply(
//                    RequestOptions
//                        .placeholderOf(R.drawable.ic_loading)
//                ).into(binding.ivImage)
//            itemView.setOnClickListener {
//                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
//                intent.putExtra(EXTRA_DATA, data)
//                val optionsCompat: ActivityOptionsCompat =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        itemView.context as Activity,
//                        Pair(binding.tvItemName, "name"),
//                        Pair(binding.ivItemPhoto, "story"),
//                        Pair(binding.tvItemDesc, "desc")
//                    )
//                itemView.context.startActivity(intent, optionsCompat.toBundle())
//            }
//        }

        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
                override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: DataItem,
                    newItem: DataItem
                ): Boolean {
                    return oldItem.placeId == newItem.placeId
                }
            }
        }

        class ListViewHolder(private val binding: CardTravelBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(data: DataItem) {
                binding.apply {
                    tvName.text = data.placeName
                    tvPrice.text = data.price.toString()
                    Glide.with(itemView.context)
                        .load(data.image)
                        .fitCenter()
                        .apply(
                            RequestOptions
                                .placeholderOf(R.drawable.ic_loading)
                        ).into(ivImage)

//                    itemView.setOnClickListener {
//                        val intent = Intent(itemView.context, DetailActivity::class.java)
//                        intent.putExtra(EXTRA_DATA, story)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(tvName, "placeName"),
                            Pair(ivImage, "image"),
                            Pair(tvPrice, "price")
                        )
//                        itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }
