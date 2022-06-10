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
import com.kotlin.sportpro.data.model.News
import com.kotlin.sportpro.ui.adapters.NewsAdapter
import com.kotlin.sportpro.ui.news.NewsViewModel
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.fragment_news_sport.*

class NewsSportFragment : Fragment() {
    private val newsViewModel: NewsViewModel by viewModels {
        InjectorObject.getNewsViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_sport, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = activity?.intent?.extras?.get("sportId") as Int
        newsViewModel.getNewsBySportId(id).observe(viewLifecycleOwner) {
            when (it) {
                is ApiResult.Success -> {
                    progress_bar.visibility = View.GONE
                    recyclerNews.visibility = View.VISIBLE
                    initList(it.data)
                }
                is ApiResult.Error -> {
                    it.throwable.message.toString()
                    Log.e("News Error", it.throwable.message.toString())
                }
                is ApiResult.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                    recyclerNews.visibility = View.GONE
                }
            }
        }



    }

    private fun showErrorPage(error: Error?) {
        Toast.makeText(context?.applicationContext, error?.message, Toast.LENGTH_LONG).show()
    }

    private fun initList(news: List<News>) {
        val adapter = NewsAdapter()
        recyclerNews.adapter = adapter
        adapter.submitList(news)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        recyclerNews.adapter = null
    }
}