package com.kotlin.sportpro.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.Sport
import kotlinx.android.synthetic.main.category_tv.view.*

class SportNameAdapter : ListAdapter<Sport, MyViewHolder>(DIFF_CALLBACK) {

    var onItemClick: (Int) -> Unit = {}

    object DIFF_CALLBACK : DiffUtil.ItemCallback<Sport>() {
        override fun areItemsTheSame(oldItem: Sport, newItem: Sport): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Sport, newItem: Sport): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            inflater.inflate(R.layout.category_tv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position).let { sport ->
            holder.itemView.nameOfCategory.text = sport.name

            holder.itemView.setOnClickListener {
                onItemClick(sport.id!!)
            }
        }
    }
}
