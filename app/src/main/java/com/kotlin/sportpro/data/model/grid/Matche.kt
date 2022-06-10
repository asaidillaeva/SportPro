package com.kotlin.sportpro.data.model.grid


import com.google.gson.annotations.SerializedName

data class Matche(
    @SerializedName("date")
    val date: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("judge")
    val judge: Judge?,
    @SerializedName("player1")
    val player1: Player?,
    @SerializedName("player1_score")
    val player1Score: Int?,
    @SerializedName("player2")
    val player2: Player?,
    @SerializedName("player2_score")
    val player2Score: Int?,
    @SerializedName("winner")
    val winner: Winner?
)