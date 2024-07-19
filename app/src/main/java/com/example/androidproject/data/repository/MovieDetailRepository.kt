package com.example.androidproject.data.repository

import com.example.androidproject.data.network.APIService
import com.example.androidproject.data.response.MovieDetailResponse
import com.example.androidproject.database.Entities.Movie
import com.example.androidproject.database.dao.MovieDao
import com.google.gson.Gson
import com.example.androidproject.utils.toMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailRepository(
    private val api: APIService,
    private val movieDao: MovieDao
) {
    suspend fun getMovieDetail(id: Int): Result<Movie> {
        return try {
            val localMovie = withContext(Dispatchers.IO) {
                movieDao.getMovie(id)
            }
            if (localMovie != null && localMovie.isDetailComplete()) {
                return Result.success(localMovie)
            }

            // If not in the database or incomplete, fetch from API
            val response = api.getMovieDetail(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val movie = body.data.toMovie()
                    updateMovieInDatabase(movie)
                    Result.success(movie)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, MovieDetailResponse::class.java)
                Result.failure(Exception(errorResponse?.description?.en ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun updateMovieInDatabase(movie: Movie) {
        println(movie.title)
        withContext(Dispatchers.IO) {
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
        }
    }

    suspend fun likeMovie(movieId: Int, isLiked: Boolean) {
        withContext(Dispatchers.IO) {
            movieDao.likeMovie(movieId, isLiked)
        }
    }
}

// Extension function to check if all details are present
fun Movie.isDetailComplete(): Boolean {
    return year != null && rated != null && released != null && runtime != null &&
            director != null && writer != null && actors != null && plot != null &&
            country != null && awards != null && metascore != null && imdbRating != null &&
            imdbVotes != null && imdbId != null && type != null
}