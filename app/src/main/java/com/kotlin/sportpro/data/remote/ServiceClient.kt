package com.kotlin.sportpro.data.remote

import com.google.gson.GsonBuilder
import com.kotlin.sportpro.data.model.*
import com.kotlin.sportpro.data.model.grid.Result
import com.kotlin.sportpro.data.model.login.LoginRequestBody
import com.kotlin.sportpro.data.model.login.LoginResponse
import com.kotlin.sportpro.data.model.profile.Player
import com.kotlin.sportpro.data.model.profile.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface ServiceClient {

    companion object {
        private const val BASE_URL = "https://sportproteam4.herokuapp.com/api/"

        private fun getGson() = GsonBuilder().setLenient().create()

        private fun getLogger() = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private fun getOkHttp() = OkHttpClient.Builder()
            .addInterceptor(getLogger())
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        private fun getRetrofit() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .build()

        operator fun invoke() = getRetrofit().create(ServiceClient::class.java)
    }

    //NEWS
    @GET("news")
    suspend fun getNews(): List<News>

    @GET("news/")
    suspend fun getNewsBySportId(@Query("sport")id: Int):List<News>

    @GET("news/{id}")
    suspend fun getNewsById(@Path("id") id: Int): News


    //EVENT
    @GET("event")
    suspend fun getEvents(): List<Event>

    @GET("event/{id}")
    suspend fun getEventById(@Path("id") id: Int): Event

    @GET("playerscategory/{id}")
    suspend fun getCategoryById(@Path("id") id: Int): Category

    @GET("event/")
    suspend fun getEventsBySportId(@Query("sport")id: Int): List<Event>

    @GET("event/")
    suspend fun getEventsByPlayerId(@Query("player") id:Int): List<Event>

    //SPORT
    @GET("sport/")
    suspend fun getSportListByCategoryId(@Query("category") id: Int): List<Sport>

    @GET("sport/{id}")
    suspend fun getSportById(@Path("id") id: Int): Sport

    @GET("sport/?is_ourchoiсe=1")
    suspend fun getOurChoiceList(): List<Sport>

    @GET("federation/")
    suspend fun getFederationBySportId(@Query("sport")id: Int): Federation

    @GET("sport")
    suspend fun getAllSportList(): List<Sport>


    //USER
    @GET("user/")
    suspend fun getUserByPhoneNumber(@Query("phone") phone: String): User

    @POST("players")
    suspend fun postPlayer(@Body player: Player): Response<Void>

    @POST("user/log")
    suspend fun login(@Body loginRequest: LoginRequestBody): LoginResponse

    @GET("user/{id}")
    suspend fun getUserById(@Path("id")id: Int): User

    @GET("players/")
    suspend fun getPlayersByTrainerId(@Query("trainer")id: Int): List<Player>

    //Players
    @GET("players/{id}")
    suspend fun getPlayerById(@Path("id")id: Int): Player

    //Grid
    @GET("grids/")
    suspend fun getGridByEventId(@Query("event")id: Int): List<Result>

    //Region
    @GET("region")
    suspend fun getAllRegions(): List<Region>

    @POST("registerplayers")
    suspend fun registerPlayer(@Body registerPlayers: RegisterPlayersRequest): RegisterPlayers
}