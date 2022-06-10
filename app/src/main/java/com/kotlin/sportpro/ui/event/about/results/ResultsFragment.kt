package com.kotlin.sportpro.ui.event.about.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.grid.CustomResult
import com.kotlin.sportpro.data.model.grid.Result
import com.kotlin.sportpro.ui.adapters.ResultAdapter
import com.kotlin.sportpro.ui.event.about.tournament.TournamentViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.error_page.*
import kotlinx.android.synthetic.main.fragment_results.*

class ResultsFragment : Fragment() {
    private val viewModel: TournamentViewModel by viewModels {
        InjectorObject.getTournamentViewModel()
    }
    val adapter = ResultAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resultRecycler.adapter = adapter
        val id: Int = requireActivity().intent.extras?.get("eventId") as Int
        viewModel.getGridByEventId(id).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    progress_bar.visibility = View.GONE
                    makeList(it.data)
                }
                is ApiResult.Error ->{
                    progress_bar.visibility = View.GONE
                    error_page.visibility = View.VISIBLE
                    errorMsgTv.text = it.throwable.message
                }
                is ApiResult.Loading ->{
                    progress_bar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun makeList(list: List<Result>){
        var listOfWinners: MutableList<CustomResult> = arrayListOf()
        for (i in list.indices) {
            for (t in list[i].matches?.indices!!)
                if (list[i].matches?.get(t)?.winner != null) {
                    var score = 0
                    if(list[i].matches?.get(t)?.player1Score!! > list[i].matches?.get(t)?.player2Score!!){
                        score = list[i].matches?.get(t)?.player1Score!!
                    }else{
                        score = list[i].matches?.get(t)?.player2Score!!
                    }
                    listOfWinners.add(CustomResult(list[i].matches?.get(t)?.winner!!,score))
                }
        }
        adapter.submitList(listOfWinners.sortedBy{it.score} as MutableList<CustomResult>)
    }
}