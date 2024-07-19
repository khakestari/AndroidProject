package com.example.androidproject.data.network

import com.example.androidproject.data.request.RegisterRequest
import com.example.androidproject.data.response.GenresListResponse
import com.example.androidproject.data.response.MovieDetailResponse
import com.example.androidproject.data.response.MoviesTopListResponse
import com.example.androidproject.data.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @POST("register")
    suspend fun register(@Body body: RegisterRequest): Response<RegisterResponse>

    @GET("genres")
    suspend fun getGenresList() : Response<GenresListResponse>

    @GET("movies/2")
    suspend fun getLastMovies () : Response<MoviesTopListResponse>

    @GET("movies/{movie_id}")
    suspend fun getMovieDetail (@Path("movie_id") id : Int) : Response <MovieDetailResponse>

    @GET("genres/{genre_id}/movies")
    suspend fun getTopMovies(@Path("genre_id") id : Int) : Response<MoviesTopListResponse>

    @GET("movies")
    suspend fun searchMovie(@Query("q") name : String) : Response<MoviesTopListResponse>
}
