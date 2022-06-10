package com.kotlin.sportpro.ui.event

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kotlin.sportpro.FilterActivity
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.Event
import com.kotlin.sportpro.ui.adapters.EventAdapter
import com.kotlin.sportpro.ui.event.about.AboutEventActivity
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.fragment_event.*


class EventFragment : Fragment() {

    private val eventViewModel: EventViewModel by viewModels {
        InjectorObject.getEventsViewModelFactory()
    }
    var adapter = EventAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerEvent.adapter = adapter

        adapter.onItemClick = { eventId ->
            var intent = Intent(context, AboutEventActivity::class.java)
            intent.putExtra("eventId", eventId)
            startActivity(intent)
        }

        eventViewModel.events.observe(viewLifecycleOwner) {
            setResult(it)
        }

        btnFilterEvent.setOnClickListener {
            val intent = Intent(activity, FilterActivity::class.java)
            startActivityForResult(intent, 1000)
        }
    }

    private fun setResult(it: ApiResult<List<Event>>?) {
        when (it) {
            is ApiResult.Success -> {
                progress_bar.visibility = View.GONE
                recyclerEvent.visibility = View.VISIBLE
                adapter.submitList(it.data)
            }

            is ApiResult.Error -> {
                it.throwable.message.toString()
                Log.e("Events Error", it.throwable.message.toString())
            }

            is ApiResult.Loading -> {
                progress_bar.visibility = View.VISIBLE
                recyclerEvent.visibility = View.GONE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == resultCode) {
            loadEventsBySport(data!!.getIntExtra("sportId", 0))
        }
    }

    private fun loadEventsBySport(id: Int) {
        eventViewModel.getEventsBySportId(id).observe(viewLifecycleOwner) {
            setResult(it)
        }
    }
}