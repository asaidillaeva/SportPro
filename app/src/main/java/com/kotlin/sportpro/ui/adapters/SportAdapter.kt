package com.kotlin.sportpro.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.Sport
import kotlinx.android.synthetic.main.sport_item_others.view.*

class SportAdapter(private val layoutName: String) :
    ListAdapter<Sport, MyViewHolder>(DIFF_CALLBACK) {

    var onItemClick: (Int) -> Unit = {}

    object DIFF_CALLBACK : DiffUtil.ItemCallback<Sport>() {
        override fun areItemsTheSame(oldItem: Sport, newItem: Sport): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Sport, newItem: Sport): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (layoutName == "our_choice"){
            return MyViewHolder(
                inflater.inflate(R.layout.sport_item, parent, false)
            )
        }
        return MyViewHolder(
            inflater.inflate(R.layout.sport_item_others, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        getItem(position).let { sport ->
            holder.itemView.sportType.text = sport.name
            holder.itemView.sportSub.text = sport.short_desc

            Glide.with(holder.itemView)
                .load(sport.photo)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .into(holder.itemView.sportImage)

            holder.itemView.setOnClickListener {
                onItemClick(sport.id)
            }
        }
    }

}
