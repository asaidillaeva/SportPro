package com.kotlin.sportpro.ui.profile.coach

import android.content.Intent
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
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.UserData
import com.kotlin.sportpro.data.model.profile.User
import com.kotlin.sportpro.ui.event.about.ViewPagerAdapter
import com.kotlin.sportpro.ui.home.SportViewModel
import com.kotlin.sportpro.ui.profile.ProfileInfoActivity
import com.kotlin.sportpro.ui.profile.UserViewModel
import com.kotlin.sportpro.ui.profile.coach.event.CoachEventFragment
import com.kotlin.sportpro.ui.profile.coach.sportsman.CoachSportsmanFragment
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.fragment_coach.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.header.profilePhotoCard

class CoachFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels {
        InjectorObject.getUserViewModelFactory()
    }

    private val sportViewModel: SportViewModel by viewModels{
        InjectorObject.getSportViewModelFactory()
    }
    var token: String? = null

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coach, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e(CoachFragment::class.java.simpleName, token.toString())
        if (UserData.of(requireContext()).getToken().toString().isEmpty() ) {
            val action = CoachFragmentDirections.actionCoachFragmentToLoginFragment()
            findNavController().navigate(action)
            return
        }

//        viewModel.getCurrentUser().observe(viewLifecycleOwner) {
//            when (it) {
//                is ApiResult.Success -> {
//                    init(it.data)
//                }

//                is ApiResult.Error -> {
//                    Toast.makeText(context, "Ошибка", Toast.LENGTH_LONG).show()
//                    Log.e("CoachFragment getUser", it.throwable.message.toString())
//                }

//                is ApiResult.Loading -> {
//                }
//            }
//        }

        init(viewModel.getCurrentUser())

        initPagerAdapter()

        header.setOnClickListener {
            val intent = Intent(context, ProfileInfoActivity::class.java)
            intent.putExtra("editable", true)
            intent.putExtra("userId", UserData(requireContext()).getId())
            startActivity(intent)
        }
    }

    private fun init(user: User) {
        profileName.text = "Тилек Сыдыков"
        profileRoleSport.text = "Тренер по " + user.sport

        sportViewModel.getSportById(viewModel.getCurrentUser().sport).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    profileRoleSport.text = "Тренер по ${it.data.name}"
                }
            }
        }


        Glide.with(requireActivity())
            .load("https://24.kg/files/media/57/57999.jpg")
            .placeholder(R.drawable.ic_user)
            .error(R.drawable.ic_user)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(true)
            .override(400, 160)
            .into(profilePhotoCard)
        
    }

    private fun initPagerAdapter() {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(CoachEventFragment(), "Соревнования")
        adapter.addFragment(CoachSportsmanFragment(), "Спортсмены")
        pagerCoach.adapter = adapter
        pagerCoach.offscreenPageLimit = 2
        tab_layout.setupWithViewPager(pagerCoach)
    }

}