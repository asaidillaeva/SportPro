package com.kotlin.sportpro.ui.profile.coach.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.sportpro.R
import kotlinx.android.synthetic.main.activity_submit_application.*

class SubmitApplicationActivity : AppCompatActivity() {
    var eventId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_application)
        eventId = intent.extras?.get("eventId") as Int
    }

    fun getResult(): Int{
        return eventId
    }
}