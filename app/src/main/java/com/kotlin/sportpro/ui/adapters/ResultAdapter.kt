package com.kotlin.sportpro.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.grid.CustomResult
import kotlinx.android.synthetic.main.result_item.view.*


class ResultAdapter :
    ListAdapter<CustomResult, MyViewHolder>(DIFF_CALLBACK) {
    object DIFF_CALLBACK : DiffUtil.ItemCallback<CustomResult>() {
        override fun areItemsTheSame(oldItem: CustomResult, newItem: CustomResult): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CustomResult, newItem: CustomResult): Boolean =
            oldItem.winner.id == newItem.winner.id
    }

    var onItemClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            inflater.inflate(R.layout.result_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position).let { result ->

            holder.itemView.resultNameTv.text =
                result.winner.name + " " + result.winner.surname
            holder.itemView.resultAgeTv.text = result.winner.age.toString()
            holder.itemView.resultCategoryTv.text = "78 кг"

//            holder.itemView.resultCityTv.text = result.winner.
            holder.itemView.resultPlaceTv.text = result.score.toString()

            holder.itemView.resultOrganizationTv.text = result.winner.organization
            Glide.with(holder.itemView)
                .load(result.winner.photo)
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .into(holder.itemView.resultImage)

        }

    }
}


