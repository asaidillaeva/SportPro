package com.kotlin.sportpro.ui.news.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kotlin.sportpro.R
import kotlinx.android.synthetic.main.activity_about_news.*

class AboutNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_news)
        val title = intent.extras!!.get("title")
        val date = intent.extras!!.get("date")
        val article = intent.extras!!.get("article")
        val photo = intent.extras!!.get("photo")

        aboutNewsDate.text = date.toString()
        aboutNewsText.text = article.toString()
        aboutNewsTitle.text = title.toString()

        Glide.with(applicationContext)
            .load(photo)
            .centerCrop()
            .placeholder(R.drawable.background_blur)
            .error(R.drawable.photo)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(aboutNewsImage)

        btnBackFromAboutNews.setOnClickListener {
            finish()

        }
    }

}