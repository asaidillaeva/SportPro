package com.kotlin.sportpro.ui.profile.coach.event

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.RegisterPlayersRequest
import com.kotlin.sportpro.ui.adapters.SubmitSportsmanAdapter
import com.kotlin.sportpro.ui.profile.UserViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.fragment_submit_sportsman.*
import kotlinx.android.synthetic.main.simple_alert.*


class SubmitSportsmanFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels {
        InjectorObject.getUserViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_submit_sportsman, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SubmitSportsmanAdapter()
        sportsmanRecycler.adapter = adapter

        var listOfUserIds = mutableListOf<Int>()

        adapter.addUserId = { it ->
            listOfUserIds.add(it)
        }
        adapter.deleteUserId = { it ->
            listOfUserIds.remove(it)
        }

        viewModel.getPlayersByTRainerId(44).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    progress_bar.visibility = View.GONE
                    adapter.submitList(it.data)
                }
                is ApiResult.Error -> {
                    Log.e("SubmitSportsmanFRag", it.throwable.message.toString())
                }
                is ApiResult.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                }
            }
        }

        btnBackFromSubmitSportsMan.setOnClickListener {
            findNavController().popBackStack()
        }

        btnSubmit.setOnClickListener {
            viewModel.registerPlayers(
                RegisterPlayersRequest(
                    listOfUserIds,
                    activity?.intent?.extras?.get("eventId") as Int
                )
            ).observe(viewLifecycleOwner) {
                when (it) {
                    is ApiResult.Success -> {
                        progress_bar.visibility = View.GONE
                        makeAlert("Ваша заявка на участие отправлена к Федерации, ждите ответа.")
                    }
                    is ApiResult.Error -> {
                        Log.e("SubmitSportsmanFRag", it.throwable.message.toString())
                    }
                    is ApiResult.Loading -> {
                        progress_bar.visibility = View.VISIBLE

                    }
                }
            }
        }
    }

    private fun makeAlert(s: String) {
        val view1: View = requireActivity().layoutInflater.inflate(R.layout.simple_alert, null)

        val alert: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(view1).create()
        }
        alert?.show()

        val ok: TextView? = view1.findViewById(R.id.onPositive)
        ok?.setOnClickListener {
            alert?.dismiss()
            requireActivity().finish()
        }
    }

}