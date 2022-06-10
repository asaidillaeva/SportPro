package com.kotlin.sportpro.ui.profile.coach.event

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.Event
import com.kotlin.sportpro.ui.event.EventViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import com.kotlin.sportpro.utils.Utils
import kotlinx.android.synthetic.main.fragment_about_event.*
import kotlinx.android.synthetic.main.fragment_about_event.categoryOfEvent
import kotlinx.android.synthetic.main.fragment_about_event.reglamentAddress
import kotlinx.android.synthetic.main.fragment_about_event.reglamentContactTv
import kotlinx.android.synthetic.main.fragment_about_event.reglamentMandateTv
import kotlinx.android.synthetic.main.fragment_about_event.reglamentProtocolTv
import kotlinx.android.synthetic.main.fragment_reglament.*


class AboutEventFragment : Fragment() {

    private val viewModel: EventViewModel by viewModels {
        InjectorObject.getEventsViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_event, container, false)
    }
    var linkToPdf = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id: Int = activity?.intent?.extras?.get("eventId") as Int
        var eventcategory = ""
        viewModel.getEventById(id).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    progress_bar.visibility = View.GONE
                    updateUi(it.data)
                }
                is ApiResult.Error -> {
                    it.throwable.message.toString()
                    Log.e("Events Error", it.throwable.message.toString())
                }
                is ApiResult.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                    ui.visibility = View.GONE
                }
            }
        }

        btnSubmit.setOnClickListener {
            val action = AboutEventFragmentDirections.actionAboutEventFragmentToSubmitSportsmanFragment(eventcategory)
            findNavController().navigate(action)
        }

        btnBackFromSubmitApplication.setOnClickListener {
            activity?.finish()
        }

        reglamentProtocolTv.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkToPdf))
            requireActivity().startActivity(browserIntent)
        }

        reglamentContactTv.setOnClickListener {
            val phoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + reglamentContactTv.text.toString() ))
            requireActivity().startActivity(phoneIntent)
        }

    }

    private fun updateUi(event: Event) {
        ui.visibility = View.VISIBLE
        categoryOfEvent.text = "Категория: ${event.eventcategory}"
        reglamentMandateTv.text =
            "C " + Utils().convertDate(event.startofWeighing) + " до " + Utils().convertDate(event.endofWeighing)
        reglamentAddress.text = event.location
        reglamentContactTv.text = event.creator.phone

        //TODO: Connect when backend ready
//        linkToPdf = event.protocol
//        reglementJudgesOfEvent.text = "Зарегистрировано: " +  event.judge.size

    }
}