package com.kotlin.sportpro.ui.event.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.kotlin.sportpro.R
import com.kotlin.sportpro.ui.event.EventViewModel
import com.kotlin.sportpro.ui.event.about.participants.ParticipantsFragment
import com.kotlin.sportpro.ui.event.about.reglament.ReglamentFragment
import com.kotlin.sportpro.ui.event.about.results.ResultsFragment
import com.kotlin.sportpro.ui.event.about.tournament.TournamentTableFragment
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.activity_about_event.*
import kotlinx.android.synthetic.main.activity_about_event.tool_text

class AboutEventActivity : AppCompatActivity() {
    private val viewModel: EventViewModel by viewModels {
        InjectorObject.getEventsViewModelFactory()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_event)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ReglamentFragment(), "Регламент")
        adapter.addFragment(CommandFragment(), "Команда")
        adapter.addFragment(ParticipantsFragment(), "Участники")
        adapter.addFragment(TournamentTableFragment(), "Сетки")
        adapter.addFragment(ResultsFragment(), "Результаты")
        pager.adapter = adapter
        pager.offscreenPageLimit = 5

        tab_layout.setupWithViewPager(pager)

        val id = intent?.extras?.get("eventId") as Int
        viewModel.getEventById(id).observe(this){
            when (it) {
                is ApiResult.Success -> {
                    tool_text.text = it.data.name
                }
                is ApiResult.Error -> {
                    Toast.makeText(applicationContext, "Ошибка", Toast.LENGTH_LONG)
                }
                is ApiResult.Loading -> {
                }
            }
        }

        btnBackFromAboutEvent.setOnClickListener {
           finish()
        }
    }
}