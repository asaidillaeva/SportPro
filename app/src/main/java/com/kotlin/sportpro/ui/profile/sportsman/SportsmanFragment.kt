package com.kotlin.sportpro.ui.profile.sportsman

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.sportpro.ui.profile.ProfileInfoActivity
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.UserData
import com.kotlin.sportpro.data.model.Event
import com.kotlin.sportpro.data.model.grid.CustomResult
import com.kotlin.sportpro.data.model.profile.Player
import com.kotlin.sportpro.data.model.profile.User
import com.kotlin.sportpro.ui.adapters.EventAdapter
import com.kotlin.sportpro.ui.event.EventViewModel
import com.kotlin.sportpro.ui.event.about.tournament.TournamentViewModel
import com.kotlin.sportpro.ui.home.SportViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.error_page.*
import kotlinx.android.synthetic.main.fragment_sportsman.*
import kotlinx.android.synthetic.main.fragment_sportsman.profileHeader
import kotlinx.android.synthetic.main.header.*

class SportsmanFragment : Fragment() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var adapter = EventAdapter()

    private val viewModel: EventViewModel by viewModels {
        InjectorObject.getEventsViewModelFactory()
    }
    private val sportViewModel: SportViewModel by viewModels {
        InjectorObject.getSportViewModelFactory()
    }


    private val tournamentViewModel: TournamentViewModel by viewModels {
        InjectorObject.getTournamentViewModel()
    }
    val currentUser: Player = Player(
        id = 10,
        user = User(
            id = 56,
            name = "Султан",
            surname = "Ахмедов",
            middlename = "-",
            photo = "https://docs.google.com/uc?id=1pWXRq6gvawofwPEoiq_19Qs3bsOuLXhy",
            age = 27,
            sex = "Мужчина",
            phone = "+996555111111",
            role = 4,
            region = 1,
            sport = 35,
            organization = "Глаз тигра",
            document = "https://docs.google.com/uc?id=178MivlCK7eh2I-mBvIOD1uJzejP35Q2U"
        ),
        trainer = 37,
        city = "Бишкек",
        weight = 60,
        photo = "https://docs.google.com/uc?id=1pWXRq6gvawofwPEoiq_19Qs3bsOuLXhy",
        organization = "Neobis"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (UserData(requireContext()).getToken().isNullOrEmpty()) {
            val action = SportsmanFragmentDirections.actionSportsmanFragmentToLoginFragment()
            findNavController().navigate(action)
            return null
        }

        return inflater.inflate(R.layout.fragment_sportsman, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sportsManEventsRecycler.adapter = adapter
        initRecycler()
        init(currentUser.user)

        onShowAllBtn.setOnClickListener {
            onShowAllBtn.background = resources.getDrawable(R.drawable.btn_red_background)
            onShowAllBtn.setTextColor(Color.parseColor("#ffffff"))

            onShowBestBtn.setBackgroundColor(Color.parseColor("#1A1919"))
            onShowBestBtn.setTextColor(Color.parseColor("#B8B8B8"))

            onShowFutureBtn.setBackgroundColor(Color.parseColor("#1A1919"))
            onShowFutureBtn.setTextColor(Color.parseColor("#B8B8B8"))

            updateRecycler("all")
        }

        onShowFutureBtn.setOnClickListener {
            onShowFutureBtn.background = resources.getDrawable(R.drawable.btn_red_background)
            onShowFutureBtn.setTextColor(Color.parseColor("#ffffff"))

            onShowAllBtn.setBackgroundColor(Color.parseColor("#1A1919"))
            onShowAllBtn.setTextColor(Color.parseColor("#B8B8B8"))

            onShowBestBtn.setBackgroundColor(Color.parseColor("#1A1919"))
            onShowBestBtn.setTextColor(Color.parseColor("#B8B8B8"))

            updateRecycler("future")
        }

        onShowBestBtn.setOnClickListener {
            onShowBestBtn.background = resources.getDrawable(R.drawable.btn_red_background)
            onShowBestBtn.setTextColor(Color.parseColor("#ffffff"))

            onShowAllBtn.setBackgroundColor(Color.parseColor("#1A1919"))
            onShowAllBtn.setTextColor(Color.parseColor("#B8B8B8"))

            onShowFutureBtn.setBackgroundColor(Color.parseColor("#1A1919"))
            onShowFutureBtn.setTextColor(Color.parseColor("#B8B8B8"))

            updateRecycler("best")
        }

        profileHeader.setOnClickListener {
            val intent = Intent(context, ProfileInfoActivity::class.java)
            intent.putExtra("playerId", currentUser.id)
            intent.putExtra("editable", false)
            startActivity(intent)
        }

    }

    private fun initRecycler() {
        viewModel.getEventsByPlayerId(currentUser.id!!).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    progress_bar.visibility = View.GONE
                    error_page.visibility = View.GONE
                    adapter.submitList(it.data)
                }
                is ApiResult.Error -> {
                    error_page.visibility = View.VISIBLE
                    errorMsgTv.text = it.throwable.message.toString()
                    Log.e("Events Error", it.throwable.message.toString())
                }
                is ApiResult.Loading -> {
                    error_page.visibility = View.GONE
                    progress_bar.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun updateRecycler(method: String) {
        when (method) {
            "all" -> {
                initRecycler()
            }
            "future" -> {
                initRecycler()
            }
            else -> {
                var list = listOf<Event>()
                viewModel.getEventsByPlayerId(currentUser.id!!).observe(viewLifecycleOwner) {
                    when (it) {
                        is ApiResult.Success -> {
                            progress_bar.visibility = View.GONE
                            error_page.visibility = View.GONE
                            list = it.data
                        }
                        is ApiResult.Error -> {
                            error_page.visibility = View.VISIBLE
                            errorMsgTv.text = it.throwable.message.toString()
                            Log.e("Events Error", it.throwable.message.toString())
                        }
                        is ApiResult.Loading -> {
                            error_page.visibility = View.GONE
                            progress_bar.visibility = View.VISIBLE
                        }
                    }
                }
                var listForAdapter = mutableListOf<Event>()
                for (i in list.indices) {
                    tournamentViewModel.getGridByEventId(list[i].id).observe(viewLifecycleOwner) {
                        when (it) {
                            is ApiResult.Success -> {
                                for (k in it.data.indices) {
                                    for (t in it.data[k].matches?.indices!!) {
                                        if (it.data[k].matches!![t].winner?.id == 57) {
                                            listForAdapter.add(list[i])
                                        }
                                    }
                                }
                            }
                            is ApiResult.Error -> {
                                Log.e("", it.throwable.message.toString())
                            }

                        }
                    }
                }
                adapter.submitList(listForAdapter)
            }
        }
    }

    private fun init(user: User) {
        profileName.text = "${user.surname} ${user.name} ${user.middlename}"
        profileRoleSport.text = "Спортсмен по ${user.sport}"

        sportViewModel.getSportById(currentUser.user.sport).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    profileRoleSport.text = "Тренер по ${it.data.name}"
                }
            }
        }

        Glide.with(requireActivity())
            .load(user.photo)
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(true)
            .override(400, 160)
            .into(profilePhotoCard)
    }
}
