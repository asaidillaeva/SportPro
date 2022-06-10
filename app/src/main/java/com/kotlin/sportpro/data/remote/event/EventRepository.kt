package com.kotlin.sportpro.data.remote.event

import com.kotlin.sportpro.data.model.Event
import com.kotlin.sportpro.data.model.Category
import com.kotlin.sportpro.utils.ApiResult

interface EventRepository {
    suspend fun getEvents(): ApiResult<List<Event>>

    suspend fun getEventById(id: Int): ApiResult<Event>

    suspend fun getCategoryById(id: Int): ApiResult<Category>

    suspend fun getEventsBySportId(id: Int): ApiResult<List<Event>>
    suspend fun getEventsByPlayerId(id: Int): ApiResult<List<Event>>
}