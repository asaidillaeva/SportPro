package com.kotlin.sportpro.ui.event.about.reglament

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.Event
import com.kotlin.sportpro.ui.event.EventViewModel
import com.kotlin.sportpro.ui.profile.UserViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import com.kotlin.sportpro.utils.Utils
import kotlinx.android.synthetic.main.fragment_reglament.*


class ReglamentFragment : Fragment() {

    private val eventViewModel: EventViewModel by viewModels {
        InjectorObject.getEventsViewModelFactory()
    }
    private val userViewModel: UserViewModel by viewModels{
        InjectorObject.getUserViewModelFactory()
    }

    var linkToPdf = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reglament, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id: Int = requireActivity().intent.extras?.get("eventId") as Int
        eventViewModel.getEventById(id).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    setValues(it.data)
                }
                is ApiResult.Error -> {

                }
                is ApiResult.Loading -> {

                }
            }
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

    private fun setValues(event: Event) {
        reglamentMandateTv.text =
            "C " + Utils(requireContext()).convertDate(event.startofWeighing) + " до " + Utils(requireContext()).convertDate(event.endofWeighing)
        reglamentAddress.text = event.location
        reglamentContactTv.text = event.creator.phone

        eventViewModel.getCategoryById(event.eventcategory).observe(viewLifecycleOwner){
            when (it){
                is ApiResult.Success ->{
                    categoryOfEvent.text = "Категория: ${it.data.name}"
                }
            }
        }

        linkToPdf = "https://judo.kg/"
        userViewModel.getUserById(event.judge).observe(viewLifecycleOwner){
            when(it){
                is ApiResult.Success ->{
                    reglementJudgesOfEvent.text = "Зарегистрирован: " +  it.data.surname + " " + it.data.name
                }
            }
        }
    }


}