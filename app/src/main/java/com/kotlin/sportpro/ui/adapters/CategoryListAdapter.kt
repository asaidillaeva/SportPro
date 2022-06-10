package com.kotlin.sportpro.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.Category
import kotlinx.android.synthetic.main.category_tv.view.*

class CategoryListAdapter : ListAdapter<Category, MyViewHolder>(DIFF_CALLBACK) {
    object DIFF_CALLBACK : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.id == newItem.id
    }

    var onItemClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            inflater.inflate(R.layout.category_tv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position).let { category ->
            holder.itemView.nameOfCategory.text = category.name

            holder.itemView.setOnClickListener {
                onItemClick(category.id!!)
            }
        }
    }

}

