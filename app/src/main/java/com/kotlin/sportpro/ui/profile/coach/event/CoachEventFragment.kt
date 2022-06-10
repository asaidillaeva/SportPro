package com.kotlin.sportpro.ui.profile.coach.event

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.Event
import com.kotlin.sportpro.data.model.profile.Player
import com.kotlin.sportpro.data.model.profile.User
import com.kotlin.sportpro.ui.adapters.EventAdapter
import com.kotlin.sportpro.ui.event.EventViewModel
import com.kotlin.sportpro.ui.profile.UserViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.error_page.*
import kotlinx.android.synthetic.main.fragment_coach_event.*

class CoachEventFragment : Fragment() {

    private var adapter = EventAdapter()

    private val eventViewModel: EventViewModel by viewModels {
        InjectorObject.getEventsViewModelFactory()
    }

    private val userViewModel: UserViewModel by viewModels {
        InjectorObject.getUserViewModelFactory()
    }

    val currentUser = User(
        id = 38, name = "Алия", surname = "Сайдиллаева", middlename = "Абдрахмановна",
        age = 18, document = null,
        phone = "+996708771864",
        photo = "",
        role = 1,
        sport = 1,
        region = 1,
        organization = "Neobis"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coach_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coachEventRecycler.adapter = adapter
        adapter.onItemClick = { eventId ->
            var intent = Intent(context, SubmitApplicationActivity::class.java)
            intent.putExtra("eventId", eventId)
            startActivity(intent)
        }

        btnShowAllEvent.setOnClickListener {
            btnShowAllEvent.background = resources.getDrawable(R.drawable.btn_red_background)
            btnShowAllEvent.setTextColor(Color.parseColor("#ffffff"))

            btnShowMyEvent.setBackgroundColor(Color.parseColor("#1A1919"))
            btnShowMyEvent.setTextColor(Color.parseColor("#B8B8B8"))

            updateRecycler("all")
        }
        btnShowMyEvent.setOnClickListener {
            btnShowMyEvent.background = resources.getDrawable(R.drawable.btn_red_background)
            btnShowMyEvent.setTextColor(Color.parseColor("#ffffff"))

            btnShowAllEvent.setBackgroundColor(Color.parseColor("#1A1919"))
            btnShowAllEvent.setTextColor(Color.parseColor("#B8B8B8"))

            updateRecycler("my")
        }
        updateRecycler("all")
    }

    private fun updateRecycler(s: String) {
        eventViewModel.getEventsBySportId(currentUser.sport).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    progress_bar.visibility = View.GONE
                    error_page.visibility = View.GONE
                    coachEventRecycler.visibility = View.VISIBLE
                    if (s == "all") {
                        adapter.submitList(it.data)
                    } else {
                        var list = getMyEvents(it.data)
                        if (list.isEmpty()) {
                            coachEventRecycler.visibility = View.GONE
                            error_page.visibility = View.VISIBLE
                            errorMsgTv.text = "Ваши спортсмены не зарегестрированы на сорвенования"
                        } else {
                            adapter.submitList(list)
                        }
                    }
                }
                is ApiResult.Error -> {
                    progress_bar.visibility = View.GONE
                    error_page.visibility = View.VISIBLE
                    errorMsgTv.text = it.throwable.message
                }
                is ApiResult.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getMyEvents(list: List<Event>): MutableList<Event> {
        var listOfEvents = mutableListOf<Event>()
        userViewModel.getPlayersByTRainerId(currentUser.id!!).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    progress_bar.visibility = View.GONE

                    for (i in list.indices) {
                        for (t in list[i].players?.indices) {
                            if (checkPlayer(list[i].players[t].id, it.data)) {
                                listOfEvents.add(list[i])
                            }
                        }
                    }
                }
            }
        }
        return listOfEvents
    }

    private fun checkPlayer(id: Int?, list: List<Player>): Boolean {
        for (i in list.indices) {
            if (id == list[i].id) {
                return true
            }
        }
        return false
    }
}