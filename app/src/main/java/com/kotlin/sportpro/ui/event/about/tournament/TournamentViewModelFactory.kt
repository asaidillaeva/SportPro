package com.kotlin.sportpro.ui.event.about.tournament

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlin.sportpro.data.remote.grid.GridRepository
import com.kotlin.sportpro.ui.profile.UserViewModel

class TournamentViewModelFactory(private val repository: GridRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TournamentViewModel::class.java) ->
                TournamentViewModel(repository) as T
            else -> throw IllegalArgumentException("Not found")
        }
    }
}