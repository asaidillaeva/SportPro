package com.kotlin.sportpro.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.kotlin.sportpro.R
import com.kotlin.sportpro.ui.adapters.SportAdapter
import com.kotlin.sportpro.ui.home.sport.SportActivity
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.activity_all_sport.*
import kotlinx.android.synthetic.main.error_page.*

class AllSportActivity : AppCompatActivity() {

    private val sportViewModel: SportViewModel by viewModels {
        InjectorObject.getSportViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_sport)

        val adapter = SportAdapter("recycler")
        recyclerAllSport.adapter = adapter

        adapter.onItemClick ={
            val intent = Intent(applicationContext, SportActivity::class.java)
            intent.putExtra("sportId", it)
            startActivity(intent)
        }

        setTextToToolbar(intent.extras?.get("sportTypeId") as Int)
        sportViewModel.getSportListByCategoryId(intent.extras?.get("sportTypeId") as Int)
            .observe(this) {
                when (it) {
                    is ApiResult.Success -> {
                        progress_bar.visibility = View.GONE
                        error_page.visibility = View.GONE
                        adapter.submitList(it.data)
                    }
                    is ApiResult.Error -> {
                        progress_bar.visibility = View.GONE
                        error_page.visibility = View.VISIBLE
                        if(it.throwable.message.toString().contains("400")){
                            errorMsgTv.text = "Пока нет видов спорта по этой категории"
                        }else {
                            errorMsgTv.text = it.throwable.message
                        }
                            Log.e("Sport  Error", it.throwable.message.toString())
                    }
                    is ApiResult.Loading -> {
                        Log.e("Loading", recyclerAllSport.toString())
                        progress_bar.visibility = View.VISIBLE
                        error_page.visibility = View.GONE
                    }
                }
            }

        btnBackAllSport.setOnClickListener {
            finish()
        }
    }

    private fun setTextToToolbar(id: Int) {
        var text = ""
        when(id){
            4 -> {
               text = "Национальные виды спорта"
            }
            1 ->{
                text = "Олимпийские виды спорта"
            }
            2 ->{
                text = "Неолимпийские виды спорта"
            }
            3 ->{
                text = "Виды спорта Пара и Сурдо (Ловз)"
            }
        }
        tool_text.text = text
    }
}