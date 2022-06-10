package com.kotlin.sportpro.data.model

import com.google.gson.annotations.SerializedName
import com.kotlin.sportpro.data.model.profile.Player


data class Event (
    @SerializedName("creator")
    val creator: Creator,
    @SerializedName("date")
    val date: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("endofWeighing")
    val endofWeighing: String,
    @SerializedName("eventcategory")
    val eventcategory: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("judge")
    val judge: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("players")
    val players: List<PlayerEntity>,
    @SerializedName("sport")
    val sport: Sport,
    @SerializedName("startofWeighing")
    val startofWeighing: String,
    @SerializedName("status")
    val status: Status)
