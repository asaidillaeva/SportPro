package com.kotlin.sportpro.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kotlin.sportpro.data.model.Federation
import com.kotlin.sportpro.data.model.Region
import com.kotlin.sportpro.data.remote.event.EventRepository
import com.kotlin.sportpro.data.remote.sport.SportRepository
import com.kotlin.sportpro.utils.ApiResult

class SportViewModel(private val sportRepository: SportRepository): ViewModel() {


    fun getSportListByCategoryId(id: Int) = liveData{
        emit(ApiResult.Loading)
        val result = sportRepository.getSportListByCategoryId(id)
        emit(result)
    }

    fun getSportById(id: Int) = liveData {
        emit(ApiResult.Loading)
        val result = sportRepository.getSportById(id)
        emit(result)
    }

    fun getOurChoiceList()= liveData {
        emit(ApiResult.Loading)
        val result = sportRepository.getOurChoiceList()
        emit(result)
    }

    fun getAllRegions() = liveData {
        emit(ApiResult.Loading)
        val result = sportRepository.getAllRegions()
        emit(result)
    }

    fun getFederationBySportId(id :Int) = liveData{
        emit(ApiResult.Loading)
        val result = sportRepository.getFederationBySportId(id)
        emit(result)
    }

}