package com.kotlin.sportpro.ui.home.sport

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kotlin.sportpro.R
import com.kotlin.sportpro.ui.home.SportViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.fragment_about_sport.*

class AboutSportFragment : Fragment() {
    private val sportViewModel: SportViewModel by viewModels {
        InjectorObject.getSportViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_sport, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = activity?.intent?.extras?.get("sportId") as Int
        sportViewModel.getSportById(id).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG)
                    init(it.data.description!!, it.data.photo!!)
                }
                is ApiResult.Error -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG)
                    Log.e("Sport  Error", it.throwable.message.toString())
                }
                is ApiResult.Loading -> {
                    Toast.makeText(context, "Loading", Toast.LENGTH_LONG)
                }
            }
        }
    }

    private fun init(description: String, photo: String) {

        aboutSportTv.text = description
        Glide.with(requireContext())
            .load(photo)
            .centerCrop()
            .placeholder(R.drawable.background_blur)
            .error(R.drawable.photo)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(aboutSportImage)

    }
}