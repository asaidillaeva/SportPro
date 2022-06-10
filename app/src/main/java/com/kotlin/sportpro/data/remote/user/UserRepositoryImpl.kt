package com.kotlin.sportpro.data.remote.user

import com.kotlin.sportpro.data.model.RegisterPlayers
import com.kotlin.sportpro.data.model.RegisterPlayersRequest
import com.kotlin.sportpro.data.model.profile.Player
import com.kotlin.sportpro.data.model.login.LoginRequestBody
import com.kotlin.sportpro.data.model.login.LoginResponse
import com.kotlin.sportpro.data.model.profile.User
import com.kotlin.sportpro.data.remote.ServiceClient
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.apiCall
import retrofit2.Response

class UserRepositoryImpl(
    private val serviceClient: ServiceClient
) : UserRepository {
    override suspend fun getUserByPhone(phoneNumber: String): ApiResult<User> {
        return apiCall { serviceClient.getUserByPhoneNumber(phoneNumber) }
    }

    override suspend fun registration(player: Player): Response<Void> {
        return serviceClient.postPlayer(player)
    }

    override suspend fun login(body: LoginRequestBody): ApiResult<LoginResponse> {
        return apiCall { serviceClient.login(body) }
    }

    override suspend fun getUserById(id: Int): ApiResult<User> {
        return apiCall { serviceClient.getUserById(id) }
    }

    override suspend fun getPlayersByTrainerId(id: Int): ApiResult<List<Player>> {
        return apiCall { serviceClient.getPlayersByTrainerId(id) }
    }

    override suspend fun getPlayerById(id: Int): ApiResult<Player> {
        return apiCall { serviceClient.getPlayerById(id) }
    }

    override suspend fun registerPlayers(
        request: RegisterPlayersRequest
    ): ApiResult<RegisterPlayers> {
        return apiCall { serviceClient.registerPlayer(request) }
    }

}