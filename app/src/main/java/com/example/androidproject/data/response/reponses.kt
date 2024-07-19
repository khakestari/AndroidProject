package com.example.androidproject.data.response

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


// response models:
data class RegisterResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("description")
    val description: Description,
    @SerializedName("data")
    val data: UserData?
) {
    data class Description(
        @SerializedName("fa")
        val fa: String,
        @SerializedName("en")
        val en: String
    )

    data class UserData(
        @SerializedName("id")
        val id: Int,
        val name: String,
        val email: String
    )
}

data class MoviesTopListResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("description")
    val description: Description,
    @SerializedName("data")
    val data: List<MovieData>,
    @SerializedName("metadata")
    val metadata: Metadata
) {
    data class Description(
        @SerializedName("fa")
        val fa: String,
        @SerializedName("en")
        val en: String
    )

    data class MovieData(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("poster")
        val poster: String,
        @SerializedName("genres")
        val genres: List<String>,
        @SerializedName("images")
        val images: List<String>
    )

    data class Metadata(
        @SerializedName("has_next")
        val hasNext: Boolean,
        @SerializedName("has_prev")
        val hasPrev: Boolean,
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("per_page")
        val perPage: Int,
        @SerializedName("page_count")
        val pageCount: Int,
        @SerializedName("total_count")
        val totalCount: Int
    )
}

data class GenresListResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("description")
    val description: Description,
    @SerializedName("data")
    val data: List<GenreData>
) {
    data class Description(
        @SerializedName("fa")
        val fa: String,
        @SerializedName("en")
        val en: String
    )

    data class GenreData(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )
}

data class MovieDetailResponse(
    val status: Int,
    val description: Description,
    val data: MovieDetail
) {
    data class Description(
        val fa: String,
        val en: String
    )

    data class MovieDetail(
        val id: Int,
        val title: String,
        val poster: String,
        val year: String,
        val rated: String,
        val released: String,
        val runtime: String,
        val director: String,
        val writer: String,
        val actors: String,
        val plot: String,
        val country: String,
        val awards: String,
        val metascore: String,
        val imdb_rating: String,
        val imdb_votes: String,
        val imdb_id: String,
        val type: String,
        val genres: List<String>,
        val images: List<String>
    )
}
