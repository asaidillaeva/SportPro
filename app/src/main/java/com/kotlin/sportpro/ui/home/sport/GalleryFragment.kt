package com.kotlin.sportpro.ui.home.sport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.sportpro.R
import com.kotlin.sportpro.ui.adapters.GalleryAdapter
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val photos = arrayListOf<String>()

        photos.add("https://img.freepik.com/free-photo/sports-tools_53876-138077.jpg?w=2000")
        photos.add("http://ru.sport-wiki.org/wp-content/themes/sportwiki/img/dzyudo.jpg")
        photos.add("https://24.kg/thumbnails/ec494/1da12/136151_w750_h_r.jpg")
        photos.add("https://24.kg/thumbnails/759e5/99b2d/137125_w750_h_r.jpg")
        photos.add("https://kabar.kg/site/assets/files/46882/do_48kg.jpg")

        var adapter: GalleryAdapter = GalleryAdapter()
        gallery_recycler.adapter = adapter
        adapter.submitList(photos)
    }
}