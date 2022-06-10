package com.kotlin.sportpro.data.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResponseWrapper<T>: Serializable {
    @SerializedName("response")
    val data: T? = null
    @SerializedName("error")
    val error: Error? = null
    
}
