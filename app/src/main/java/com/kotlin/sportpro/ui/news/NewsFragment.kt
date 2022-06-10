package com.kotlin.sportpro.ui.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kotlin.sportpro.R
import androidx.fragment.app.viewModels
import com.kotlin.sportpro.FilterActivity
import com.kotlin.sportpro.data.model.News
import com.kotlin.sportpro.ui.adapters.NewsAdapter
import com.kotlin.sportpro.utils.ApiResult
import com.kotlin.sportpro.utils.InjectorObject
import kotlinx.android.synthetic.main.fragment_news.*


class NewsFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModels {
        InjectorObject.getNewsViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel.news.observe(viewLifecycleOwner) {
            setResult(it)
        }

        btnFilterNews.setOnClickListener {
            val intent = Intent(activity, FilterActivity::class.java)
            startActivityForResult(intent, 1000)
        }
    }

    private fun setResult(it: ApiResult<List<News>>?) {
        when (it) {
            is ApiResult.Success -> {
                progress_bar.visibility = View.GONE
                recyclerNews.visibility = View.VISIBLE
                initList(it.data)
            }
            is ApiResult.Error -> {
                showErrorPage(it.throwable)
                Log.e("News Error", it.throwable.message.toString())
            }
            is ApiResult.Loading -> {
                progress_bar.visibility = View.VISIBLE
                recyclerNews.visibility = View.GONE
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == resultCode) {
            loadDataBySport(data!!.getIntExtra("sportId", 0))
        }
    }

    private fun loadDataBySport(id: Int) {
        newsViewModel.getNewsBySportId(id).observe(viewLifecycleOwner) {
            setResult(it)
        }
    }

    private fun showErrorPage(error: Throwable) {
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