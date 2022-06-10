package com.kotlin.sportpro.data.remote.news

import com.kotlin.sportpro.data.model.News
import com.kotlin.sportpro.data.remote.ServiceClient
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.apiCall


class NewsRepositoryImpl(
    private val serviceClient: ServiceClient
) : NewsRepository {
    override suspend fun getNews(): ApiResult<List<News>> {
        return apiCall { serviceClient.getNews() }
    }

    override suspend fun getNewsBySportId(id: Int): ApiResult<List<News>> {
        return apiCall { serviceClient.getNewsBySportId(id) }

    }
}
