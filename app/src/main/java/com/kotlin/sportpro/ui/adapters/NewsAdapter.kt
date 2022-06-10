package com.kotlin.sportpro.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.News
import com.kotlin.sportpro.ui.news.about.AboutNewsActivity
import kotlinx.android.synthetic.main.news_item.view.*


class NewsAdapter() :
    ListAdapter<News, MyViewHolder>(DIFF_CALLBACK) {

    object DIFF_CALLBACK : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean =
            oldItem.id == newItem.id
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            inflater.inflate(R.layout.news_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position).let { news ->
            holder.itemView.newsTitle.text = news.title
            holder.itemView.newsDate.text = news.dateofadd.subSequence(0, 10)
            holder.itemView.newsSportType.text = news.sport.name

            Glide.with(holder.itemView)
                .load(news.photo)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .override(400, 160)
                .into(holder.itemView.newsImage)

            holder.itemView.setOnClickListener {
                var intent = Intent(holder.itemView.context, AboutNewsActivity::class.java)
                intent.putExtra("title", news.title)
                    .putExtra("date", news.dateofadd.subSequence(0, 10))
                    .putExtra("article", news.article)
                    .putExtra("photo", news.photo)
                holder.itemView.context.startActivity(intent)

            }
        }
    }


}

