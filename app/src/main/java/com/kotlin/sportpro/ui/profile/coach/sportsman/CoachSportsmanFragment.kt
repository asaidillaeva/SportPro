package com.kotlin.sportpro.ui.profile.coach.sportsman

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.UserData
import com.kotlin.sportpro.ui.adapters.CoachPlayersAdapter
import com.kotlin.sportpro.ui.profile.ProfileInfoActivity
import com.kotlin.sportpro.ui.profile.UserViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.fragment_coach_sportsman.*

class CoachSportsmanFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels {
        InjectorObject.getUserViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coach_sportsman, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CoachPlayersAdapter()
        coachSportsmanRecycler.adapter = adapter

        //TODO Submit list of PlayerEntity to adapter

        viewModel.getPlayersByTRainerId(44).observe(viewLifecycleOwner){
            when (it){
                is ApiResult.Success ->{
                    adapter.submitList(it.data)
                }
            }
        }

        adapter.onItemClick = { it ->
            val intent = Intent(context, ProfileInfoActivity::class.java)
            intent.putExtra("playerId", it)
            intent.putExtra("editable", true)
            startActivity(intent)
        }

        btnAddSportsman.setOnClickListener {
            val intent = Intent(context, AddSportsmanActivity::class.java)
            startActivity(intent)
        }
    }
}