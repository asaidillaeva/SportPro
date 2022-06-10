package com.kotlin.sportpro.data.remote.user

import com.kotlin.sportpro.data.model.RegisterPlayers
import com.kotlin.sportpro.data.model.RegisterPlayersRequest
import com.kotlin.sportpro.data.model.profile.Player
import com.kotlin.sportpro.data.model.login.LoginRequestBody
import com.kotlin.sportpro.data.model.login.LoginResponse
import com.kotlin.sportpro.data.model.profile.User
import com.kotlin.sportpro.utils.ApiResult
import retrofit2.Response

interface UserRepository {
    suspend fun getUserByPhone(phoneNumber: String): ApiResult<User>

    suspend fun registration(player: Player): Response<Void>

    suspend fun login(body: LoginRequestBody): ApiResult<LoginResponse>

    suspend fun getUserById(id:Int): ApiResult<User>

    suspend fun getPlayersByTrainerId(id: Int): ApiResult<List<Player>>

    suspend fun getPlayerById(id: Int): ApiResult<Player>
    suspend fun registerPlayers(request: RegisterPlayersRequest): ApiResult<RegisterPlayers>

}
