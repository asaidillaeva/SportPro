package com.kotlin.sportpro.ui.profile.judge

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.UserData
import com.kotlin.sportpro.data.model.Event
import com.kotlin.sportpro.ui.adapters.EventAdapter
import com.kotlin.sportpro.ui.event.EventViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.error_page.*
import kotlinx.android.synthetic.main.fragment_judge.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.activity_add_sportsman.profilePhotoCard as profilePhotoCard1

class JudgeFragment : Fragment() {
    private lateinit var mContext: Context
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val eventViewModel: EventViewModel by viewModels {
        InjectorObject.getEventsViewModelFactory()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (UserData(requireContext()).getToken().isNullOrEmpty()) {
            val action = JudgeFragmentDirections.actionJudgeFragmentToLoginFragment()
            findNavController().navigate(action)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_judge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = view.context
        initAdapter()
        initViews()
    }

    private fun initViews() {
        profileName.text = "Акматов Азамат Акматович"
        profileRoleSport.text = "Судья по дзюдо"
        Picasso.with(mContext)
            .load("https://media-center.kg/uploads/news/images/1586340402.258048695.jpeg")
            .into(profilePhotoCard)
    }

    private fun initAdapter() {
        val adapter = EventAdapter()
        judgeEventsRecycler.adapter = adapter
        var list = mutableListOf<Event>()

        eventViewModel.getEventsBySportId(1).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    progress_bar.visibility = View.GONE
                    error_page.visibility = View.GONE
                    judgeEventsRecycler.visibility = View.VISIBLE
                    adapter.submitList(it.data)

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
        adapter.onItemClick = { eventId ->
            val action = JudgeFragmentDirections.actionJudgeFragmentToTournamentTableFragment2()
            findNavController().navigate(action)
        }
    }

}