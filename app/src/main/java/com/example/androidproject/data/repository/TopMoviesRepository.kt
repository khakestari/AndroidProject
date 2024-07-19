package com.example.androidproject.data.repository

import com.example.androidproject.data.network.APIService
import com.example.androidproject.data.response.MoviesTopListResponse
import com.google.gson.Gson

class TopMoviesRepository(private val api: APIService) {
    suspend fun getTopMovies(genreId: Int): Result<MoviesTopListResponse> {
        return try {
            val response = api.getTopMovies(genreId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, MoviesTopListResponse::class.java)
                Result.failure(Exception(errorResponse?.description?.en ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
