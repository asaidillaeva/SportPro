package com.kotlin.sportpro.data.model.grid


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id") val id: Int,
    @SerializedName("stage") val stage: String?,
    @SerializedName("event") val event: Int?,
    @SerializedName("matches") val matches: List<Matche>?
)