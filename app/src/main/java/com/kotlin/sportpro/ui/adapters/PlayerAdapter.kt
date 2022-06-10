package com.kotlin.sportpro.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.PlayerEntity
import kotlinx.android.synthetic.main.sportsman_item.view.*
import java.util.*
import kotlin.collections.ArrayList


class PlayerAdapter :
    ListAdapter<PlayerEntity, MyViewHolder>(DIFF_CALLBACK), Filterable {
    object DIFF_CALLBACK : DiffUtil.ItemCallback<PlayerEntity>() {
        override fun areItemsTheSame(oldItem: PlayerEntity, newItem: PlayerEntity): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: PlayerEntity, newItem: PlayerEntity): Boolean =
            oldItem.id == newItem.id
    }

    lateinit var filterList: List<PlayerEntity>
    var onItemClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            inflater.inflate(R.layout.sportsman_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position).let { player ->

            holder.itemView.playerName.text =
                player.name + " " + player.surname + " " + player.middlename
//            holder.itemView.playerCity.text = player.city.toString()
            holder.itemView.playerOrganization.text = player.organization.toString()
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterList = currentList
                } else {
                    val resultList = ArrayList<PlayerEntity>()
                    for (row in currentList) {
                        if (row.name!!.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    filterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                submitList(filterList)
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as List<PlayerEntity>
                submitList(filterList)
            }

        }
    }


}


