package com.example.androidproject.presentation.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidproject.R
import com.example.androidproject.database.Entities.Movie

class MovieAdapter(
    val movies: MutableList<Movie>,
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val moviePoster: ImageView = itemView.findViewById(R.id.moviePoster)
        private val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        private val movieRating: TextView = itemView.findViewById(R.id.movieRating)
        private val movieCountry: TextView = itemView.findViewById(R.id.movieCountry)
        private val movieYear: TextView = itemView.findViewById(R.id.movieYear)

        fun bindData(movie: Movie) {
            // Load image using Glide (add Glide dependency if not already added)
            Glide.with(itemView.context)
                .load(movie.poster)
                .into(moviePoster)

            movieTitle.text = movie.title
            movieRating.text = movie.imdbRating ?: "N/A"
            movieCountry.text = movie.country ?: "N/A"
            movieYear.text = movie.released ?: "N/A"

            itemView.setOnClickListener { onItemClick(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(movies[position])
    }
}