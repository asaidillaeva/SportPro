package com.kotlin.sportpro.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.sportpro.R
import com.kotlin.sportpro.ui.adapters.ImageAdapter
import com.kotlin.sportpro.ui.adapters.SportAdapter
import com.kotlin.sportpro.ui.home.sport.SportActivity
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private val sportViewModel: SportViewModel by viewModels {
        InjectorObject.getSportViewModelFactory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing Recyclers with sport items
        initOurChoice()
        initRecycler(34, recyclerNationalSports)
        initRecycler(1, recyclerOlympicSports)
        initRecycler(35, recyclerNotOlympicSports)
        initRecycler(36, recyclerParaSurdo)


        //onClick methods for buttons All
        btnAllOlympic.setOnClickListener { openAllSports(1) }
        btnAllNotOlympic.setOnClickListener { openAllSports(35) }
        btnAllParaSurdo.setOnClickListener { openAllSports(36) }
        btnAllNational.setOnClickListener { openAllSports(34) }

        scrollView.isFillViewport = true

        val images = intArrayOf(
            R.drawable.imgone,
            R.drawable.imgtwo,
            R.drawable.imgthree)
        bannerPager.adapter = ImageAdapter(images)


    }


    fun openAllSports(id: Int) {
        val intent = Intent(context, AllSportActivity::class.java)
        intent.putExtra("sportTypeId", id)
        startActivity(intent)
    }

    fun initRecycler(categoryId: Int, recycler: RecyclerView) {
        val adapter = SportAdapter("recycler")
        recycler.adapter = adapter

        adapter.onItemClick = { it ->
            val intent = Intent(context, SportActivity::class.java)
            intent.putExtra("sportId", it)
            startActivity(intent)
        }

        sportViewModel.getSportListByCategoryId(categoryId).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
//                    progress_bar.visibility = View.GONE
                    adapter.submitList(it.data)
                }
                is ApiResult.Error -> {
                    Log.e("Sport  Error", it.throwable.message.toString())
                }
                is ApiResult.Loading -> {
                    Log.e("Loading", recycler.toString())
//                    progress_bar.visibility = View.VISIBLE
                }
            }
        }
    }

    fun initOurChoice() {

        val adapter = SportAdapter("our_choice")
        recyclerOurChoice.adapter = adapter

        adapter.onItemClick = { it ->
            val intent = Intent(context, SportActivity::class.java)
            intent.putExtra("sportId", it)
            startActivity(intent)
        }

        sportViewModel.getOurChoiceList().observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
//                    progress_bar.visibility = View.GONE
                    adapter.submitList(it.data)
                }
                is ApiResult.Error -> {

                    Log.e("Sport  Error", it.throwable.message.toString())
                }
                is ApiResult.Loading -> {
                    Log.e("Loading", recyclerOurChoice.toString())
//                    progress_bar.visibility = View.VISIBLE
                }
            }
        }
    }

}