package com.kotlin.sportpro.ui.home.sport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.kotlin.sportpro.R
import com.kotlin.sportpro.ui.event.about.ViewPagerAdapter
import com.kotlin.sportpro.ui.home.SportViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.activity_sport.*

class SportActivity : AppCompatActivity() {
    private val sportViewModel: SportViewModel by viewModels {
        InjectorObject.getSportViewModelFactory()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sport)
        sportViewModel.getSportById(intent.extras!!.get("sportId")as Int).observe(this){
            when (it) {
                is ApiResult.Success -> {
                   nameOfSport.text = it.data.name
                }
                is ApiResult.Error -> {
                    Log.e("Sport  Error", it.throwable.message.toString())
                }
                is ApiResult.Loading -> {
                }
            }
        }

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(AboutSportFragment(), "О спорте")
        adapter.addFragment(FederationFragment(), "Федерация")
        adapter.addFragment(NewsSportFragment(), "Новости")
        adapter.addFragment(EventSportFragment(), "Соревнования")
        adapter.addFragment(GalleryFragment(), "Галерея")

        pager.adapter = adapter
        pager.offscreenPageLimit = 4

        tab_layout.setupWithViewPager(pager)




        btnBackFromSport.setOnClickListener {
            finish()
        }
    }
}