package com.kotlin.sportpro.data.remote.news

import com.kotlin.sportpro.data.model.News
import com.kotlin.sportpro.utils.ApiResult

interface NewsRepository {
    suspend fun getNews(): ApiResult<List<News>>
    suspend fun getNewsBySportId(id: Int): ApiResult<List<News>>
}