package com.kotlin.sportpro.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlin.sportpro.data.remote.news.NewsRepository

class NewsViewModelFactory(private val newsRepository: NewsRepository): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NewsViewModel::class.java) ->
                NewsViewModel(newsRepository) as T
            else -> throw IllegalArgumentException("Not found")
        }
    }
}