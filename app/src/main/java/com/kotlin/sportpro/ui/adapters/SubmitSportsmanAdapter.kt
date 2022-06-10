package com.kotlin.sportpro.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.profile.Player
import com.kotlin.sportpro.data.model.profile.User
import kotlinx.android.synthetic.main.sportsman_application_item.view.*

class SubmitSportsmanAdapter : ListAdapter<Player, MyViewHolder>(DIFF_CALLBACK) {
    object DIFF_CALLBACK : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean =
            oldItem.id == newItem.id
    }

    var addUserId: (Int) -> Unit = {}
    var deleteUserId: (Int) -> Unit = {}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            inflater.inflate(R.layout.sportsman_application_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position).let { player ->
            holder.itemView.nameTv.text = player.user.name
            holder.itemView.unCheckedIv.setOnClickListener {
                holder.itemView.unCheckedIv.visibility = View.GONE
                holder.itemView.whiteIv.visibility = View.VISIBLE
                player.user.id?.let { it -> addUserId(it) }
            }

            holder.itemView.whiteIv.setOnClickListener {
                holder.itemView.unCheckedIv.visibility = View.VISIBLE
                holder.itemView.whiteIv.visibility = View.GONE
                player.user.id?.let { it -> deleteUserId(it) }
            }


        }
    }
}