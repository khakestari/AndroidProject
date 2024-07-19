package com.example.androidproject.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidproject.R
import android.widget.SearchView
import com.example.androidproject.MainActivity

class SearchScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? MainActivity.NavigationVisibilityListener)?.setNavigationVisibility(true)
        val view = inflater.inflate(R.layout.fragment_search_screen, container, false)

        // Get a reference to the SearchView and expand it
//        val searchView: SearchView = view.findViewById(R.id.searchIcon)
//        searchView.isIconified = false
//        searchView.clearFocus() // Optional: Remove focus from the SearchView

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity.NavigationVisibilityListener)?.setNavigationVisibility(true)
    }
}

