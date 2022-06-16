package com.kotlin.sportpro.ui.event.about.tournament

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.grid.Result
import com.kotlin.sportpro.data.model.Stage
import com.kotlin.sportpro.data.model.Tournament
import com.kotlin.sportpro.data.model.grid.Matche
import com.kotlin.sportpro.ui.adapters.StageAdapter
import com.kotlin.sportpro.ui.adapters.TournamentAdapter
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.error_page.*
import kotlinx.android.synthetic.main.fragment_tournament_table.*

class TournamentTableFragment : Fragment() {

    private var mType: String = ""
    private val stageAdapter = StageAdapter()
    private val tournamentAdapter = TournamentAdapter()
    private var list = mutableListOf<MutableList<Tournament>>()
    private var stagePosition = 0

    private val viewModel: TournamentViewModel by viewModels {
        InjectorObject.getTournamentViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tournament_table, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stageRecycler.adapter = stageAdapter

        stageAdapter.onItemClick = {
            stagePosition = it
            setStageFocusOn(stagePosition)
            tournamentAdapter.submitList(list[stagePosition])
        }

        tournamentRecycler.adapter = tournamentAdapter

        tournamentAdapter.onMatch1Click = {
            val action = TournamentTableFragmentDirections.actionTournamentTableFragment2ToJudgingFragment()
            findNavController().navigate(action)
        }
        tournamentAdapter.onMatch2Click = {
            val action = TournamentTableFragmentDirections.actionTournamentTableFragment2ToJudgingFragment()
            findNavController().navigate(action)
        }

        tournamentAdapter.onPreviousItemClick = {
            stagePosition--
            setStageFocusOn(stagePosition)
            tournamentAdapter.submitList(list[stagePosition])
        }

        tournamentAdapter.onNextItemClick = {
            stagePosition++
            setStageFocusOn(stagePosition)
            tournamentAdapter.submitList(list[stagePosition])
        }

        var id: Int = (requireActivity().intent.extras?.get("eventId") ?: -1) as Int
        if (id == -1) {
            mType = "judge"
            id = 4
        }
        viewModel.getGridByEventId(id).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    progress_bar.visibility = View.GONE
                    error_page.visibility = View.GONE
                    stageRecycler.visibility = View.VISIBLE
                    tournamentRecycler.visibility = View.VISIBLE

                    if (it.data.isNotEmpty()) {
                        stageAdapter.submitList(makeStagesList(it.data))
                        makeTournamentList(it.data)
                        tournamentAdapter.submitList(list[stagePosition])
                    } else {
                        error_page.visibility = View.VISIBLE
                        errorMsgTv.text = "Сетка не готова"
                        stageRecycler.visibility = View.INVISIBLE
                        tournamentRecycler.visibility = View.GONE
                    }
                }
                is ApiResult.Error -> {
                    progress_bar.visibility = View.GONE
                    error_page.visibility = View.VISIBLE
                    errorMsgTv.text = it.throwable.message
                    stageRecycler.visibility = View.INVISIBLE
                    tournamentRecycler.visibility = View.GONE
                }
                is ApiResult.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                    error_page.visibility = View.GONE
                    stageRecycler.visibility = View.INVISIBLE
                    tournamentRecycler.visibility = View.GONE
                }
            }
        }
    }

    private fun setStageFocusOn(stagePosition: Int) {
        var stageList = stageAdapter.currentList
        if (stageList.size == 1) {
            stageList[0].onFocus = true
        } else {
            for (i in stageList.indices) {
                var onFocus = false
                if (i == stagePosition) onFocus = true
                stageList[i].onFocus = onFocus
            }
        }
        stageAdapter.notifyDataSetChanged()
        stageRecycler.smoothScrollToPosition(stagePosition)
    }

    private fun makeTournamentList(results: List<Result>) {
        for (i in results!!.indices) {
            val listOfTournament = mutableListOf<Tournament>()
            for (t in results[i].matches!!.indices step 2) {
                val match1 = results[i].matches?.get(t)!!
                var match2: Matche? = null
                if (results[i].matches?.size != 1) {
                    match2 = results[i].matches!![t + 1]
                }
                var hasPrevious = false
                if (i != results.size - 1) {
                    hasPrevious = true
                }

                var hasNext = true
                if (i == 0) {
                    hasNext = false
                }

                if (results.size == 1) {
                    hasNext = false
                    hasPrevious = false
                }

                val tournament = Tournament(
                    hasNext,
                    hasPrevious,
                    results[i].stage!!,
                    match1,
                    match2
                )
                listOfTournament.add(tournament)
            }
            list.add(listOfTournament)
        }
        list = list.asReversed()
    }

    private fun makeStagesList(results: List<Result>): MutableList<Stage> {
        var list = mutableListOf<Stage>()
        if (results!!.size == 1) {
            list.add(Stage(results[0].id, results[0].stage.toString(), onFocus = true))
        } else {
            for (i in results!!.indices) {
                var onFocus = false
                if (i == results!!.size - 1) onFocus = true
                list.add(Stage(results[i].id, results[i].stage.toString(), onFocus))
            }
        }
        return list.asReversed()
    }
}