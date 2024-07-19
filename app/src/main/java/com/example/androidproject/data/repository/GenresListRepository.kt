package com.example.androidproject.data.repository

import com.example.androidproject.data.network.APIService
import com.example.androidproject.data.response.GenresListResponse
import com.google.gson.Gson

class GenresListRepository(private val api: APIService) {
    suspend fun getGenresList(): Result<GenresListResponse> {
        return try {
            val response = api.getGenresList()
            if (response.isSuccessful) {
                val body = response.body()
                println(body)
                println("14")
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, GenresListResponse::class.java)
                Result.failure(Exception(errorResponse?.description?.en ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
