package com.kotlin.sportpro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.kotlin.sportpro.ui.adapters.SportNameAdapter
import com.kotlin.sportpro.ui.home.SportViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity() {

    private val viewModel: SportViewModel by viewModels {
        InjectorObject.getSportViewModelFactory()
    }
    var adapter = SportNameAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        sportsRecycler.adapter = adapter

        adapter.onItemClick = { it ->
            intent.putExtra("sportId", it)
            setResult(1000, intent)
            finish()
        }

        nationalBtn.setOnClickListener {
            tool_text.text = "Национальные виды спорта"
            loadData(4)
        }

        olympicBtn.setOnClickListener {
            tool_text.text = "Олимпийские виды спорта"
            loadData(1)
        }

        notOlympicBtn.setOnClickListener {
            tool_text.text = "Неолимпийские виды спорта"
            loadData(2)
        }

        paraSurdoBtn.setOnClickListener {
            tool_text.text = "Виды спорта Пара и Сурдо"
            loadData(3)
        }

        btnBackFromFilter.setOnClickListener {
            finish()
        }

    }

    private fun loadData(id: Int) {
        choose_sport_type.visibility = View.GONE
        sportsRecycler.visibility = View.VISIBLE
        viewModel.getSportListByCategoryId(id).observe(this) {
            when (it) {
                is ApiResult.Success -> {
                    progress_bar.visibility = View.GONE
                    adapter.submitList(it.data)
                }
                is ApiResult.Error -> {
                    if(it.throwable.message.toString().contains("400")){
                        progress_bar.visibility = View.GONE
                        Toast.makeText(applicationContext, "Нет спорта по этому виду", Toast.LENGTH_SHORT).show()
                    }
                    Log.e("Sport  Error", it.throwable.message.toString())
                }
                is ApiResult.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                }
            }
        }
    }
}