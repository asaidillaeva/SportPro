package com.kotlin.sportpro.ui.event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kotlin.sportpro.data.remote.event.EventRepository
import com.kotlin.sportpro.utils.ApiResult

class EventViewModel(private val eventRepository: EventRepository): ViewModel() {
    val events = liveData {
        emit(ApiResult.Loading)
        val result = eventRepository.getEvents()
        emit(result)
    }

    fun getEventById(id: Int) = liveData{
        emit(ApiResult.Loading)
        val result = eventRepository.getEventById(id)
        emit(result)
    }

    fun getCategoryById(id: Int) = liveData {
        emit(ApiResult.Loading)
        val result = eventRepository.getCategoryById(id)
        emit(result)
    }

    fun getEventsBySportId(id: Int) = liveData {
        emit(ApiResult.Loading)
        val result = eventRepository.getEventsBySportId(id)
        emit(result)
    }

    fun getEventsByPlayerId(id: Int) = liveData {
        emit(ApiResult.Loading)
        val result = eventRepository.getEventsByPlayerId(id)
        emit(result)
    }

}
