package com.kotlin.sportpro.data.remote.event

import com.kotlin.sportpro.data.model.Event
import com.kotlin.sportpro.data.model.Category
import com.kotlin.sportpro.data.remote.ServiceClient
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.apiCall

class EventRepositoryImpl(private val serviceClient: ServiceClient): EventRepository {
    override suspend fun getEvents(): ApiResult<List<Event>> {
        return apiCall { serviceClient.getEvents() }
    }

    override suspend fun getEventById(id: Int): ApiResult<Event> {
        return apiCall { serviceClient.getEventById(id) }
    }

    override suspend fun getCategoryById(id: Int): ApiResult<Category> {
        return apiCall { serviceClient.getCategoryById(id) }
    }

    override suspend fun getEventsBySportId(id: Int): ApiResult<List<Event>> {
        return apiCall { serviceClient.getEventsBySportId(id) }

    }

    override suspend fun getEventsByPlayerId(id: Int): ApiResult<List<Event>> {
        return apiCall { serviceClient.getEventsByPlayerId(id) }
    }

}