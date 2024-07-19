package com.example.androidproject.presentation.favorite

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidproject.MainActivity
import com.example.androidproject.R

class FavoriteScreenFragment : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            (activity as? MainActivity.NavigationVisibilityListener)?.setNavigationVisibility(true)
            return inflater.inflate(R.layout.fragment_favorite_screen, container, false)
        }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity.NavigationVisibilityListener)?.setNavigationVisibility(true)
    }
    }