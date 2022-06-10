package com.kotlin.sportpro.ui.event.about.tournament

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kotlin.sportpro.data.remote.grid.GridRepository
import com.kotlin.sportpro.data.remote.user.UserRepository
import com.kotlin.sportpro.utils.ApiResult

class TournamentViewModel(private val repository: GridRepository): ViewModel() {

    fun getGridByEventId(id: Int) = liveData {
        emit(ApiResult.Loading)
        val result = repository.getGridByEventId(id)
        emit(result)
    }
}