package com.kotlin.sportpro.ui.home.sport

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.Federation
import com.kotlin.sportpro.ui.home.SportViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.fragment_federation.*

class FederationFragment : Fragment() {
    private val sportViewModel: SportViewModel by viewModels {
        InjectorObject.getSportViewModelFactory()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_federation, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val id = activity?.intent?.extras?.get("sportId") as Int
//        sportViewModel.getFederationBySportId(id).observe(viewLifecycleOwner) {
//            when (it) {
//                is ApiResult.Success -> {
//                    Toast.makeText(context, "Success", Toast.LENGTH_LONG)
////                    init(it.data)
//                }
//                is ApiResult.Error -> {
//                    Toast.makeText(context, "Ошибка", Toast.LENGTH_LONG)
//                    Log.e("Sport  Error", it.throwable.message.toString())
//                }
//                is ApiResult.Loading -> {
//                    Toast.makeText(context, "Loading", Toast.LENGTH_LONG)
//
//                }
//            }
//        }
    }

    private fun init(federation: Federation) {
        historyOfFederationTv.text =federation.description
        addressTv.text = federation.contacts
        generalAssistantTv.text = federation.admin?.surname + " " + federation.admin?.name
        generalAssistantContactsTv.text = federation.admin?.phone

    }

}