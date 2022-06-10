package com.kotlin.sportpro.ui.event.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kotlin.sportpro.R
import com.kotlin.sportpro.ui.event.EventViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.fragment_command.*
import kotlinx.android.synthetic.main.fragment_reglament.*

class CommandFragment : Fragment() {

    private val viewModel: EventViewModel by viewModels {
        InjectorObject.getEventsViewModelFactory()
    }

    val linkToPdf = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_command, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id: Int = requireActivity().intent.extras?.get("eventId") as Int

        viewModel.getEventById(id).observe(viewLifecycleOwner){
            when (it) {
                is ApiResult.Success -> {
//                    linkToPdf = it.data.uriToCommands
                }
                else -> {

                }
            }
        }

        commandsProtocolTv.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkToPdf))
            requireActivity().startActivity(browserIntent)
        }

    }
}