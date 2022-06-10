package com.kotlin.sportpro.ui.profile.judge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.UserData
import com.kotlin.sportpro.data.model.Event
import com.kotlin.sportpro.ui.adapters.EventAdapter
import kotlinx.android.synthetic.main.fragment_judge.*

class JudgeFragment : Fragment() {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (UserData(requireContext()).getToken().isNullOrEmpty()){
            val action = JudgeFragmentDirections.actionJudgeFragmentToLoginFragment()
            findNavController().navigate(action)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_judge, container, false)
        initAdapter()
        
    }

    private fun initAdapter() {
        val adapter = EventAdapter()
        judgeEventsRecycler.adapter = adapter
        var list = mutableListOf<Event>()



        adapter.submitList(list)
        adapter.onItemClick = { eventId ->
            val action = JudgeFragmentDirections.actionJudgeFragmentToTournamentTableFragment2(eventId)
            findNavController().navigate(action)
        }
    }


}