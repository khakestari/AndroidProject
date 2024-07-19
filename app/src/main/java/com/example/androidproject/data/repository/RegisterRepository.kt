package com.example.androidproject.data.repository

import com.example.androidproject.data.network.APIService
import com.example.androidproject.data.request.RegisterRequest
import com.example.androidproject.data.response.RegisterResponse
import com.google.gson.Gson

class RegisterRepository(private val api: APIService) {
    suspend fun register(user: RegisterRequest): Result<RegisterResponse> {
        return try {
            val response = api.register(user)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
                Result.failure(Exception(errorResponse?.description?.en ?: "Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}