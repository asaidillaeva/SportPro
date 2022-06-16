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


        viewModel.getPlayersByTRainerId(44).observe(viewLifecycleOwner){
            when (it){
                is ApiResult.Success ->{
                    it.data[0].photo = "https://static.ca-news.org/upload/ennews/6/594766.1499945316.b.jpg"
                    it.data[1].photo = "https://weproject.media/upload/medialibrary/382/382d16f6cda525ad922b11d482b6bdb4.jpg"
                    it.data[5].photo = "https://kabar.kg/site/assets/files/44552/img_20181026_231616.jpg"
                    it.data[3].photo = "https://static.ca-news.org/upload/ennews/4/671554.1653899602.b.jpg"
                    it.data[4].photo = ""
                    it.data[6].photo = "https://weproject.media/upload/medialibrary/935/9355965b2a42abf23f6f5c95d01233e9.jpg"
                    it.data[7].photo = "https://static.ca-news.org/upload/ennews/9/661119.1627962293.b.jpg"
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