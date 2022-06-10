package com.kotlin.sportpro.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlin.sportpro.data.remote.event.EventRepository
import com.kotlin.sportpro.data.remote.sport.SportRepository
import com.kotlin.sportpro.ui.event.EventViewModel

class SportViewModelFactory (private val repository: SportRepository): ViewModelProvider.Factory
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SportViewModel::class.java) ->
                SportViewModel(repository) as T
            else -> throw IllegalArgumentException("Not found")
        }
    }
}