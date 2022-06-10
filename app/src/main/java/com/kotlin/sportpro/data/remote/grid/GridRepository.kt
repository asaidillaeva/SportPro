package com.kotlin.sportpro.data.remote.grid

import com.kotlin.sportpro.data.model.grid.Result
import com.kotlin.sportpro.utils.ApiResult

interface GridRepository {
    suspend fun getGridByEventId(id: Int): ApiResult<List<Result>>


}