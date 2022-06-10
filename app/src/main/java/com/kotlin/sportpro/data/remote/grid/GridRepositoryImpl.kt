package com.kotlin.sportpro.data.remote.grid

import com.kotlin.sportpro.data.model.grid.Result
import com.kotlin.sportpro.data.remote.ServiceClient
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.apiCall

class GridRepositoryImpl(private val serviceClient: ServiceClient): GridRepository {
    override suspend fun getGridByEventId(id: Int): ApiResult<List<Result>> {
        return apiCall { serviceClient.getGridByEventId(id) }
    }
}