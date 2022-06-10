package com.kotlin.sportpro.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.profile.Player
import kotlinx.android.synthetic.main.activity_profile_info.view.*
import kotlinx.android.synthetic.main.event_item.view.*
import kotlinx.android.synthetic.main.player_item.view.*
import kotlinx.android.synthetic.main.result_item.view.*
import kotlinx.android.synthetic.main.result_item.view.resultAgeTv
import kotlinx.android.synthetic.main.result_item.view.resultCategoryTv
import kotlinx.android.synthetic.main.result_item.view.resultCityTv
import kotlinx.android.synthetic.main.result_item.view.resultNameTv
import kotlinx.android.synthetic.main.result_item.view.resultOrganizationTv

class CoachPlayersAdapter : ListAdapter<Player, MyViewHolder>(DIFF_CALLBACK) {
    object DIFF_CALLBACK : DiffUtil.ItemCallback<Player>() {
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean =
            oldItem.id == newItem.id
    }

    var onItemClick: (Int) -> Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            inflater.inflate(R.layout.player_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position).let { player ->

            holder.itemView.resultNameTv.text =
                player.user.name + " " + player.user.surname
            holder.itemView.resultAgeTv.text = player.user.age.toString()
            holder.itemView.resultCategoryTv.text = player.weight.toString() + "кг"
            holder.itemView.resultCityTv.text = player.city
            holder.itemView.resultOrganizationTv.text = player.user.organization
          Glide.with(holder.itemView)
                .load(player.photo)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .override(100, 100)
                .into(holder.itemView.playerImage)

            holder.itemView.setOnClickListener {
                onItemClick(player.id!!)
            }
        }
    }

}
