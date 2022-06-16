package com.kotlin.sportpro.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.kotlin.sportpro.R
import kotlinx.android.synthetic.main.gallery_item.view.*

class GalleryAdapter : ListAdapter<String, MyViewHolder>(DIFF_CALLBACK) {
    object DIFF_CALLBACK : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            inflater.inflate(R.layout.gallery_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView.image)
            .load(getItem(position))
            .into(holder.itemView.image)
    }
}
