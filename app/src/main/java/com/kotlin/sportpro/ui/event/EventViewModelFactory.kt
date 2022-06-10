package com.kotlin.sportpro.ui.event


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlin.sportpro.data.remote.event.EventRepository

class EventViewModelFactory (private val repository: EventRepository): ViewModelProvider.Factory
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(EventViewModel::class.java) ->
                EventViewModel(repository) as T
            else -> throw IllegalArgumentException("Not found")
        }
    }
}