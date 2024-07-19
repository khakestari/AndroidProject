package com.example.androidproject.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String
)

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey val id: Int,
    val title: String,
    @ColumnInfo(name = "poster_url") val poster: String,
    @TypeConverters(Converters::class)
    val genres: List<String>,
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "screenshots_url") val images: List<String>,
    @ColumnInfo(name = "is_liked", defaultValue = "0") val isLiked: Boolean = false,
    val year: String?,
    val rated: String?,
    val released: String?,
    val runtime: String?,
    val director: String?,
    val writer: String?,
    val actors: String?,
    val plot: String?,
    val country: String?,
    val awards: String?,
    val metascore: String?,
    @ColumnInfo(name = "imdb_rating") val imdbRating: String?,
    @ColumnInfo(name = "imdb_votes") val imdbVotes: String?,
    @ColumnInfo(name = "imdb_id") val imdbId: String?,
    val type: String?
)