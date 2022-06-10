package com.kotlin.sportpro.data.model

import com.google.gson.annotations.SerializedName

data class RegisterPlayers(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("event")
    val eventId: Int
)
