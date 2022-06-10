package com.kotlin.sportpro.ui.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.Event
import com.kotlin.sportpro.utils.Utils
import kotlinx.android.synthetic.main.event_item.view.*


class EventAdapter :
    ListAdapter<Event, MyViewHolder>(DIFF_CALLBACK) {

    var onItemClick: (Int) -> Unit = {}

    object DIFF_CALLBACK : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            inflater.inflate(R.layout.event_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position).let { event ->
            holder.itemView.eventTitle.text = event.name
            holder.itemView.eventStatus.text = event.status.name

            when (event.status.toString()) {
                //Регистрация открыта
                "1" -> {
                    holder.itemView.eventStatus.setBackgroundResource(R.drawable.background_green_blur)
                    holder.itemView.eventStartDate.text = Utils().convertDate(event.startofWeighing)
                    holder.itemView.eventEndDate.text = Utils().convertDate(event.endofWeighing)

                }
                //Предстоящие
                "2" -> {
                    holder.itemView.eventStatus.setBackgroundResource(R.drawable.background_orange_blur)
                    holder.itemView.eventStartDate.text = Utils().convertDate(event.date)
                    holder.itemView.eventEndDate.visibility = View.GONE
                }
                //Завершен
                "3" -> {
                    holder.itemView.eventStatus.setBackgroundResource(R.drawable.background_red_blur)
                    holder.itemView.eventStartDate.text = Utils().convertDate(event.date)
                    holder.itemView.eventEndDate.visibility = View.GONE
                }
            }

            holder.itemView.eventSpotType.text = event.sport.name

            Glide.with(holder.itemView)
                .load(event.photo)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .override(400, 160)
                .into(holder.itemView.eventImage)

            holder.itemView.setOnClickListener {
                onItemClick(event.id!!)
            }
        }
    }
}
