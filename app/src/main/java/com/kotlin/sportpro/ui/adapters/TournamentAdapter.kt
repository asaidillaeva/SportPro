package com.kotlin.sportpro.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kotlin.sportpro.R
import com.kotlin.sportpro.data.model.Tournament
import kotlinx.android.synthetic.main.tournament_item.view.*

class TournamentAdapter : ListAdapter<Tournament, MyViewHolder>(DIFF_CALLBACK) {
    object DIFF_CALLBACK : DiffUtil.ItemCallback<Tournament>() {
        override fun areItemsTheSame(oldItem: Tournament, newItem: Tournament): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Tournament, newItem: Tournament): Boolean =
            oldItem.match1.id == newItem.match1.id
    }

    var onNextItemClick: (String) -> Unit = {}
    var onPreviousItemClick: (String) -> Unit = {}
    var onMatch1Click: (Int) -> Unit = {}
    var onMatch2Click: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(
            inflater.inflate(R.layout.tournament_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position).let { tournament ->

            holder.itemView.match1PlayerName1.text =
                tournament.match1.player1?.surname + " " + tournament.match1.player1?.name
            holder.itemView.match1PlayerScore1.text = tournament.match1.player1Score.toString()

            holder.itemView.match1PlayerName2.text =
                tournament.match1.player2?.surname + " " + tournament.match1.player2?.name
            holder.itemView.match1PlayerScore2.text = tournament.match1.player2Score.toString()

            if(tournament.match2 != null) {
                holder.itemView.match2PlayerName1.text =
                    tournament.match2?.player1?.surname + " " + tournament.match2?.player1?.name
                holder.itemView.match2PlayerScore1.text =
                    tournament.match2?.player1Score.toString()

                holder.itemView.match2PlayerName2.text =
                    tournament.match2?.player2?.surname + " " + tournament.match2?.player2?.name
                holder.itemView.match2PlayerScore2.text =
                    tournament.match2?.player2Score.toString()
            }else{
                holder.itemView.match2.visibility =View.GONE
                holder.itemView.previous2.visibility = View.GONE

            }
            if (!tournament.hasPrevious) {
                holder.itemView.previous1.visibility = View.GONE
                holder.itemView.previous2.visibility = View.GONE
            } else {
                holder.itemView.previous1.setOnClickListener {
                    onPreviousItemClick(tournament.stage)
                }
                holder.itemView.previous2.setOnClickListener {
                    onPreviousItemClick(tournament.stage)
                }
            }
            if (!tournament.hasNext) {
                holder.itemView.next.visibility = View.GONE
            } else {
                holder.itemView.next.setOnClickListener {
                    onNextItemClick(tournament.stage)
                }
            }


            holder.itemView.match1.setOnClickListener {
                onMatch1Click(tournament.match1.id!!)
            }

            holder.itemView.match2.setOnClickListener {
                onMatch2Click(tournament.match2?.id!!)
            }
        }
    }

}


