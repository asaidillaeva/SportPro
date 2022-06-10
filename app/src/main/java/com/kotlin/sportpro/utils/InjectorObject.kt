package com.kotlin.sportpro.utils

import com.kotlin.sportpro.data.remote.user.CoachRepository
import com.kotlin.sportpro.data.remote.ServiceClient
import com.kotlin.sportpro.data.remote.event.EventRepository
import com.kotlin.sportpro.data.remote.event.EventRepositoryImpl
import com.kotlin.sportpro.data.remote.grid.GridRepository
import com.kotlin.sportpro.data.remote.grid.GridRepositoryImpl
import com.kotlin.sportpro.data.remote.news.NewsRepository
import com.kotlin.sportpro.data.remote.news.NewsRepositoryImpl
import com.kotlin.sportpro.data.remote.sport.SportRepository
import com.kotlin.sportpro.data.remote.sport.SportRepositoryImpl
import com.kotlin.sportpro.data.remote.user.UserRepository
import com.kotlin.sportpro.data.remote.user.UserRepositoryImpl
import com.kotlin.sportpro.ui.event.EventViewModelFactory
import com.kotlin.sportpro.ui.event.about.tournament.TournamentViewModelFactory
import com.kotlin.sportpro.ui.home.SportViewModelFactory
import com.kotlin.sportpro.ui.news.NewsViewModelFactory
import com.kotlin.sportpro.ui.profile.UserViewModelFactory

object InjectorObject {

    private val serviceClient = ServiceClient()

    private val newsRepository: NewsRepository = NewsRepositoryImpl(serviceClient)
    fun getNewsViewModelFactory() = NewsViewModelFactory(newsRepository)

    private val EVENT_REPOSITORY: EventRepository = EventRepositoryImpl(serviceClient)
    fun getEventsViewModelFactory() = EventViewModelFactory(EVENT_REPOSITORY)

    private val SPORT_REPOSITORY: SportRepository = SportRepositoryImpl(serviceClient)
    fun getSportViewModelFactory() = SportViewModelFactory(SPORT_REPOSITORY)

    private val USER_REPOSITORY: UserRepository = UserRepositoryImpl(serviceClient)
    fun getUserViewModelFactory()  = UserViewModelFactory(USER_REPOSITORY)

    private val GRID_REPOSITORY: GridRepository = GridRepositoryImpl(serviceClient)
    fun getTournamentViewModel()  = TournamentViewModelFactory(GRID_REPOSITORY)


//    private val COACH_REPOSITORY: CoachRepository = CoachRepositoryImpl(serviceClient)
//    fun getCoachViewModelFactory() = EventViewModelFactory(EVENT_REPOSITORY)
}
