package com.example.androidproject.utils

import com.example.androidproject.data.response.MovieDetailResponse
import com.example.androidproject.data.response.MoviesTopListResponse
import com.example.androidproject.database.Entities.Movie


// Extension function to convert MovieData to Movie entity

fun MovieDetailResponse.MovieDetail.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        poster = poster,
        year = year,
        rated = rated,
        released = released,
        runtime = runtime,
        director = director,
        writer = writer,
        actors = actors,
        plot = plot,
        country = country,
        awards = awards,
        metascore = metascore,
        imdbRating = imdb_rating,
        imdbVotes = imdb_votes,
        imdbId = imdb_id,
        type = type,
        genres = genres,
        images = images
    )
}