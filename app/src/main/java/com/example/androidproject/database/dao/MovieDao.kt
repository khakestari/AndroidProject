package com.example.androidproject.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidproject.database.Entities.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Query("SELECT * FROM movie")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovie(movieId: Int): Movie?

    @Query("UPDATE movie SET is_liked = :isLiked WHERE id = :movieId")
    fun likeMovie(movieId: Int, isLiked: Boolean): Int

    @Query("""
        UPDATE movie SET 
        title = :title, 
        poster_url = :posterUrl, 
        genres = :genres, 
        screenshots_url = :screenshotsUrl, 
        year = :year, 
        rated = :rated, 
        released = :released, 
        runtime = :runtime, 
        director = :director, 
        writer = :writer, 
        actors = :actors, 
        plot = :plot, 
        country = :country, 
        awards = :awards, 
        metascore = :metascore, 
        imdb_rating = :imdbRating, 
        imdb_votes = :imdbVotes, 
        imdb_id = :imdbId, 
        type = :type 
        WHERE id = :id
    """)
    fun update(
        id: Int,
        title: String,
        posterUrl: String,
        genres: List<String>,
        screenshotsUrl: List<String>,
        year: String?,
        rated: String?,
        released: String?,
        runtime: String?,
        director: String?,
        writer: String?,
        actors: String?,
        plot: String?,
        country: String?,
        awards: String?,
        metascore: String?,
        imdbRating: String?,
        imdbVotes: String?,
        imdbId: String?,
        type: String?
    ): Int
}
