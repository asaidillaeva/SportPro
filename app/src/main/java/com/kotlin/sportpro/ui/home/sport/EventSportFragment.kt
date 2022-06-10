package com.kotlin.sportpro.ui.home.sport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.Event
import com.kotlin.sportpro.ui.adapters.EventAdapter
import com.kotlin.sportpro.ui.event.EventViewModel
import com.kotlin.sportpro.ui.event.about.AboutEventActivity
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.fragment_events_sport.*
import kotlinx.android.synthetic.main.fragment_events_sport.progress_bar

class EventSportFragment : Fragment() {

    private val eventViewModel: EventViewModel by viewModels {
        InjectorObject.getEventsViewModelFactory()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events_sport, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = activity?.intent?.extras?.get("sportId") as Int

        eventViewModel.getEventsBySportId(id).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    progress_bar.visibility = View.GONE
                    recyclerEvent.visibility = View.VISIBLE
                    initList(it.data)
                }
                is ApiResult.Error -> {
                    if(it.throwable.message.toString().contains("400")){
                        progress_bar.visibility = View.GONE
                        Toast.makeText(context, "Нет соревнований по этому виду спорта", Toast.LENGTH_SHORT).show()
                    }
                    Log.e("Events Error", it.throwable.message.toString())
                }
                is ApiResult.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                    recyclerEvent.visibility = View.GONE
                }
            }
        }
    }

    private fun initList(data: List<Event>) {
        var adapter = EventAdapter()
        adapter.submitList(data)
        recyclerEvent.adapter = adapter

        adapter.onItemClick = { eventId ->
            var intent = Intent(context, AboutEventActivity::class.java)
            intent.putExtra("eventId", eventId)
            startActivity(intent)
        }
    }
}