package com.kotlin.sportpro.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.kotlin.sportpro.data.model.RegisterPlayersRequest
import com.kotlin.sportpro.data.model.profile.Player
import com.kotlin.sportpro.data.model.login.LoginRequestBody
import com.kotlin.sportpro.data.model.profile.User
import com.kotlin.sportpro.data.remote.user.UserRepository
import com.kotlin.sportpro.utils.ApiResult
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(private val userRepository: UserRepository) :
    ViewModel() {
    val codeResponse = MutableLiveData<Response<Void>>()


    fun getUserByPhoneNumber(phone: String) = liveData {
        emit(ApiResult.Loading)
        val result = userRepository.getUserByPhone(phone)
        emit(result)
    }

    fun login(body: LoginRequestBody) = liveData {
        emit(ApiResult.Loading)
        val result = userRepository.login(body)
        emit(result)
    }

    fun getUserById(id: Int) = liveData {
        emit(ApiResult.Loading)
        val result = userRepository.getUserById(id)
        emit(result)
    }

    fun getPlayersByTRainerId(id: Int) = liveData {
        emit(ApiResult.Loading)
        val result = userRepository.getPlayersByTrainerId(id)
        emit(result)
    }

    fun getPlayerById(id: Int) = liveData {
        emit(ApiResult.Loading)
        val result = userRepository.getPlayerById(id)
        emit(result)
    }
    fun registration(player: Player) {
        viewModelScope.launch {
            userRepository.registration(player).let {
                codeResponse.value = it
            }
        }
    }

    fun getCurrentUser(): User {
        return User(
            id = 38, name = "Алия", surname = "Сайдиллаева", middlename = "Абдрахмановна",
            age = 18, document = null,
            phone = "+996708771864",
            photo = "",
            role =1,
            sport = 1,
            region = 1,
            organization = "Neobis"
        )
    }

    fun registerPlayers(request: RegisterPlayersRequest) = liveData {
        emit(ApiResult.Loading)
        val result = userRepository.registerPlayers(request)
        emit(result)
    }

//    fun updateUser(user: User) = liveData {
//        emit(ApiResult.Loading)
//        val result = userRepository.updateUser(user)
//        emit(result)
//    }
}
