package com.kotlin.sportpro.data.remote.sport

import com.kotlin.sportpro.data.model.Federation
import com.kotlin.sportpro.data.model.Region
import com.kotlin.sportpro.data.model.Sport
import com.kotlin.sportpro.data.remote.ServiceClient
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.apiCall

class SportRepositoryImpl(
    private val serviceClient: ServiceClient
) : SportRepository {


    override suspend fun getSportById(id: Int): ApiResult<Sport> {
        return apiCall { serviceClient.getSportById(id) }
    }

    override suspend fun getSportListByCategoryId(id: Int): ApiResult<List<Sport>> {
        return apiCall { serviceClient.getSportListByCategoryId(id) }
    }

    override suspend fun getOurChoiceList(): ApiResult<List<Sport>> {
        return apiCall { serviceClient.getOurChoiceList() }

    }

    override suspend fun getAllSportList(): ApiResult<List<Sport>> {
        return apiCall { serviceClient.getAllSportList() }
    }

    override suspend fun getAllRegions(): ApiResult<List<Region>> {
        return apiCall { serviceClient.getAllRegions() }
    }

    override suspend fun getFederationBySportId(id: Int): ApiResult<Federation> {
        return apiCall { serviceClient.getFederationBySportId(id) }
    }

}