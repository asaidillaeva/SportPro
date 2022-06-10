package com.kotlin.sportpro.data.remote.sport

import com.kotlin.sportpro.data.model.Federation
import com.kotlin.sportpro.data.model.Region
import com.kotlin.sportpro.data.model.Sport
import com.kotlin.sportpro.utils.ApiResult

interface SportRepository {
    suspend fun getSportById(id: Int): ApiResult<Sport>
    suspend fun getSportListByCategoryId(id: Int): ApiResult<List<Sport>>
    suspend fun getOurChoiceList(): ApiResult<List<Sport>>
    suspend fun getAllSportList(): ApiResult<List<Sport>>
    suspend fun getAllRegions(): ApiResult<List<Region>>
    suspend fun  getFederationBySportId(id: Int): ApiResult<Federation>
}