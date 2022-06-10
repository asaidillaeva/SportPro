package com.kotlin.sportpro.ui.profile.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.kotlin.sportpro.data.model.login.LoginRequestBody
import com.kotlin.sportpro.data.remote.user.UserRepository
import com.kotlin.sportpro.utils.ApiResult
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val userRepository: UserRepository) :
    ViewModel() {


    fun login(body: LoginRequestBody) = liveData {
        emit(ApiResult.Loading)
        val result = userRepository.login(body)
        emit(result)
    }

}