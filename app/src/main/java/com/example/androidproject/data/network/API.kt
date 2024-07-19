package com.example.androidproject.data.network

import com.example.androidproject.data.network.API.Companion.Constants.BASEURL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class API {
    companion object {
        private val myAppClient: OkHttpClient
            get() = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .writeTimeout(Constants.CONNECTIONTIME, TimeUnit.SECONDS)
                .readTimeout(Constants.CONNECTIONTIME, TimeUnit.SECONDS)
                .connectTimeout(Constants.CONNECTIONTIME, TimeUnit.SECONDS)
                .build()

        private val apiBuilder: Retrofit.Builder
            get() = Retrofit.Builder()
                .client(myAppClient)
                .addConverterFactory(GsonConverterFactory.create(gson))

        val baseUserService: APIService by lazy {
            apiBuilder
                .baseUrl(BASEURL)
                .build()
                .create(APIService::class.java)
        }

        private val gson: Gson get() = GsonBuilder().setLenient().create()

        private val interceptor: HttpLoggingInterceptor
            get() = HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

        private object Constants {
            const val BASEURL = "https://api.cseshirazu307.ir/"
            const val CONNECTIONTIME: Long = 60
        }
    }
}