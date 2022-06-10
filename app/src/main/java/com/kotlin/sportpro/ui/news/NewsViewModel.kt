package com.kotlin.sportpro.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kotlin.sportpro.data.remote.news.NewsRepository
import com.kotlin.sportpro.utils.ApiResult

class NewsViewModel(private val newsRepository: NewsRepository) :
    ViewModel() {
    fun getNewsBySportId(id: Int)= liveData {
        emit(ApiResult.Loading)
        val result = newsRepository.getNewsBySportId(id)
        emit(result)
    }

    val news = liveData {
        emit(ApiResult.Loading)
        val result = newsRepository.getNews()
        emit(result)
    }

}