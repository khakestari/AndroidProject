package com.example.androidproject.data.repository

import android.util.Log
import com.example.androidproject.data.network.APIService
import com.example.androidproject.data.response.MoviesTopListResponse
import com.example.androidproject.database.Entities.Movie
import com.example.androidproject.database.dao.MovieDao
import com.example.androidproject.utils.toMovie
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LastMoviesRepository(
    private val api: APIService,
    private val movieDao: MovieDao
) {
    suspend fun getLastMovies(): Result<List<Movie>> {
        return try {
            val response = api.getLastMovies()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val movies = body.data.map { it.toMovie() }
                    fetchAndUpdateMovieDetails(movies)
                    saveMoviesToDatabase(movies)
                    Result.success(movies)
                } else {
                    Log.e("LastMoviesRepository", "Empty response body")
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, MoviesTopListResponse::class.java)
                Log.e("LastMoviesRepository", "Error response: ${errorResponse?.description?.en ?: "Unknown error"}")
                Result.failure(Exception(errorResponse?.description?.en ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Log.e("LastMoviesRepository", "Exception in getLastMovies", e)
            Result.failure(e)
        }
    }

    private suspend fun saveMoviesToDatabase(movies: List<Movie>) {
        Log.d("LastMoviesRepository", "Saving movies to database: $movies")
        try {
            movieDao.insertAll(movies)
            Log.d("LastMoviesRepository", "Movies saved to database successfully")
        } catch (e: Exception) {
            Log.e("LastMoviesRepository", "Exception in saveMoviesToDatabase", e)
        }
    }

    private suspend fun fetchAndUpdateMovieDetails(movies: List<Movie>) {
        movies.forEach { movie ->
            try {
                val detailResponse = api.getMovieDetail(movie.id)
                print("57")
//                println(detailResponse)
                if (detailResponse.isSuccessful) {
                    val movieDetail = detailResponse.body()
//                    println(movieDetail)
                    if (movieDetail != null) {
                        val updatedMovie = movie.copy(
                            year = movieDetail.data.year,
                            rated = movieDetail.data.rated,
                            released = movieDetail.data.released,
                            runtime = movieDetail.data.runtime,
                            director = movieDetail.data.director,
                            writer = movieDetail.data.writer,
                            actors = movieDetail.data.actors,
                            plot = movieDetail.data.plot,
                            country = movieDetail.data.country,
                            awards = movieDetail.data.awards,
                            metascore = movieDetail.data.metascore,
                            imdbRating = movieDetail.data.imdb_rating,
                            imdbVotes = movieDetail.data.imdb_votes,
                            imdbId = movieDetail.data.imdb_id,
                            type = movieDetail.data.type
                        )
                        println("ahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh")
                        println(updatedMovie.title)
//                        updateMovie(updatedMovie)
                    } else {
                        Log.e("LastMoviesRepository", "Movie detail response body is null")
                    }
                } else {
                    Log.e("LastMoviesRepository", "Failed to fetch movie details for id ${movie.id}")
                }
            } catch (e: Exception) {
                Log.e("LastMoviesRepository", "Exception in fetchAndUpdateMovieDetails for movie id ${movie.id}", e)
            }
        }
    }

    suspend fun getMoviesFromDatabase(): List<Movie> {
        return try {
            movieDao.getAllMovies().also {
                Log.d("LastMoviesRepository", "Fetched movies from database: $it")
            }
        } catch (e: Exception) {
            Log.e("LastMoviesRepository", "Exception in getMoviesFromDatabase", e)
            emptyList()
        }
    }

    suspend fun updateMovie(movie: Movie) {
        Log.d("LastMoviesRepository", "Updating movie: $movie")
        withContext(Dispatchers.IO) {
            try {
                movieDao.update(
                    id = movie.id,
                    title = movie.title,
                    posterUrl = movie.poster,
                    genres = movie.genres,
                    screenshotsUrl = movie.images,
                    year = movie.year,
                    rated = movie.rated,
                    released = movie.released,
                    runtime = movie.runtime,
                    director = movie.director,
                    writer = movie.writer,
                    actors = movie.actors,
                    plot = movie.plot,
                    country = movie.country,
                    awards = movie.awards,
                    metascore = movie.metascore,
                    imdbRating = movie.imdbRating,
                    imdbVotes = movie.imdbVotes,
                    imdbId = movie.imdbId,
                    type = movie.type
                )
                Log.d("LastMoviesRepository", "Movie updated successfully")
            } catch (e: Exception) {
                Log.e("LastMoviesRepository", "Exception in updateMovie", e)
            }
        }
    }
}

fun MoviesTopListResponse.MovieData.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        poster = poster,
        genres = genres,
        images = images,
        // Other fields can be set to null or default values
        year = null,
        rated = null,
        released = null,
        runtime = null,
        director = null,
        writer = null,
        actors = null,
        plot = null,
        country = null,
        awards = null,
        metascore = null,
        imdbRating = null,
        imdbVotes = null,
        imdbId = null,
        type = null
    )
}

