package com.kotlin.sportpro.data.model

import com.google.gson.annotations.SerializedName

data class RegisterPlayersRequest(
    @SerializedName("players") val players: List<Int>,
    @SerializedName("event") val eventId: Int
)
