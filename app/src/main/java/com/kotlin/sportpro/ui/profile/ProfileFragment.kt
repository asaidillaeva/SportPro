package com.kotlin.sportpro.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.UserData
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels {
        InjectorObject.getUserViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!UserData(requireActivity()).getToken().isNullOrEmpty() || !UserData(requireActivity()).getPhone().isNullOrEmpty()) {
            openUserProfile()
        }
        else{
            progress_bar.visibility = View.GONE
            buttons_page.visibility = View.VISIBLE
        }

        btnCoach.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToCoachFragment()
            findNavController().navigate(action)
        }
        btnJudge.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToJudgeFragment()

            findNavController().navigate(action)
        }
        btnSportsman.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToSportsmanFragment()
            findNavController().navigate(action)
        }
    }

    private fun openUserProfile() {
        val phone = UserData(requireActivity()).getPhone()
        viewModel.getUserByPhoneNumber(phone!!).observe(viewLifecycleOwner){
            when(it){
                is ApiResult.Success ->{
                    openProfile(it.data.role)
                }
                is ApiResult.Error ->{
                    progress_bar.visibility = View.GONE
                    buttons_page.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Войдите в систему", Toast.LENGTH_SHORT)
                    return@observe
                }
                is ApiResult.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                    buttons_page.visibility = View.GONE
                }
            }
        }

    }

    private fun openProfile(role: Int) {
        val action: NavDirections = when (role) {
            1 -> {
                ProfileFragmentDirections.actionProfileFragmentToCoachFragment()
            }
            4 -> {
                ProfileFragmentDirections.actionProfileFragmentToSportsmanFragment()
            }
            5 -> {
                ProfileFragmentDirections.actionProfileFragmentToJudgeFragment()
            }
            else -> {
                return
            }
        }
        findNavController().navigate(action)
    }

}