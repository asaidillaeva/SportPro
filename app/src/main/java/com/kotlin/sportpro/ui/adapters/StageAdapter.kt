package com.kotlin.sportpro.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.Stage
import kotlinx.android.synthetic.main.button_item.view.*
import kotlinx.android.synthetic.main.tournament_item.view.*

class StageAdapter : ListAdapter<Stage, MyViewHolder>(DIFF_CALLBACK) {
    object DIFF_CALLBACK : DiffUtil.ItemCallback<Stage>() {
        override fun areItemsTheSame(oldItem: Stage, newItem: Stage): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Stage, newItem: Stage): Boolean =
            oldItem.id == newItem.id
    }

    var onItemClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            inflater.inflate(R.layout.button_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position).let { stage ->
            if (stage.onFocus) {
                holder.itemView.stageName.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.red
                    )
                )
                holder.itemView.stageName.requestFocus()
            }else{
                holder.itemView.stageName.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.mask
                    )
                )
            }
            if(stage.name == "1/1") {
                holder.itemView.stageName.text = "Финал"
            }else{
                holder.itemView.stageName.text = stage.name
            }
            holder.itemView.setOnClickListener {
                submitList(currentList)
                holder.itemView.stageName.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.red
                    )
                )
                onItemClick(position)
                focusOn(position)
            }

        }
    }

    private fun focusOn(position: Int) {

    }

}



