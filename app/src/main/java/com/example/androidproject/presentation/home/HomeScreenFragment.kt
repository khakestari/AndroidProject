package com.example.androidproject.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidproject.MainActivity
import com.example.androidproject.databinding.FragmentHomeScreenBinding
import com.example.androidproject.presentation.home.adapter.GenreAdapter
import com.example.androidproject.presentation.home.adapter.MovieAdapter

class HomeScreenFragment : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var viewModel: HomeScreenViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var genreAdapter: GenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = requireActivity() as MainActivity
        val factory = HomeScreenViewModelFactory(mainActivity.appDatabase)
        viewModel = ViewModelProvider(this, factory)[HomeScreenViewModel::class.java]
        setupRecyclerView()
        observeViewModel()

        viewModel.getLastMovies()
        viewModel.getGenresList()
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(mutableListOf()) { movie ->
            // Handle movie click
        }
        binding.recyclerMovies.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context)
        }

        genreAdapter = GenreAdapter(mutableListOf())
        binding.recyclerGenres.apply {
            adapter = genreAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeViewModel() {
        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            movieAdapter.movies.clear()
            movieAdapter.movies.addAll(movies)
            movieAdapter.notifyDataSetChanged()
        }

        viewModel.genres.observe(viewLifecycleOwner) { genres ->
            genreAdapter.updateGenres(genres)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity.NavigationVisibilityListener)?.setNavigationVisibility(true)
    }
}