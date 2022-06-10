package com.kotlin.sportpro.ui.event.about.participants

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.kotlin.sportpro.R
import com.kotlin.sportpro.ui.adapters.PlayerAdapter
import com.kotlin.sportpro.ui.event.EventViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.fragment_participants.*

class ParticipantsFragment : Fragment() {

    private val viewModel: EventViewModel by viewModels {
        InjectorObject.getEventsViewModelFactory()
    }
    val adapter = PlayerAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_participants, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventParticipantsRecycler.adapter = adapter
        loadData()

        swipe_refresh_layout.setOnRefreshListener {
            loadData()
        }

        searchParticipant.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun loadData() {
        val id = activity?.intent?.extras?.get("eventId") as Int
        viewModel.getEventById(id).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    progress_bar.visibility = View.GONE
                    if (it.data.players.isEmpty() && it.data.players.size < 0) {
                        Toast.makeText(context, "Нет зарегистрированных игроков", Toast.LENGTH_LONG)
                            .show()
                    }else {
                        adapter.submitList(it.data.players)

                    }
                    swipe_refresh_layout.isRefreshing = false
                }
                is ApiResult.Error -> {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(context, "Ошибка", Toast.LENGTH_LONG).show()
                    Log.e("Participants  Error 81", it.throwable.message.toString())
                }
                is ApiResult.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                }
            }
        }
    }




}

