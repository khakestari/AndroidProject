package com.example.androidproject.presentation.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidproject.data.model.Genre
import com.example.androidproject.data.network.API
import com.example.androidproject.data.repository.GenresListRepository
import com.example.androidproject.data.repository.LastMoviesRepository
import com.example.androidproject.data.response.GenresListResponse
import com.example.androidproject.data.response.MoviesTopListResponse
import com.example.androidproject.database.AppDatabase
import com.example.androidproject.database.Entities.Movie
import com.example.androidproject.database.dao.MovieDao
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val lastMoviesRepository: LastMoviesRepository,
    private val genresListRepository: GenresListRepository
) : ViewModel() {

    // LiveData for movies
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    // LiveData for genres
    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> get() = _genres

    init {
        loadMoviesFromDatabase()
        getGenresList()
    }

    fun getGenresList() {
        viewModelScope.launch {
            val result = genresListRepository.getGenresList()
            result.onSuccess { response ->
                // Map GenreData to Genre
                val genresList = response.data.map { it.toGenre() }
                _genres.postValue(genresList)
            }.onFailure { error ->
                // Handle error
            }
        }
    }

    fun getLastMovies() {
        viewModelScope.launch {
            val result = lastMoviesRepository.getLastMovies()
            result.onSuccess { movies ->
                _movies.postValue(movies)
            }.onFailure { error ->
                // Handle error
            }
        }
    }

    fun loadMoviesFromDatabase() {
        viewModelScope.launch {
            val databaseMovies = lastMoviesRepository.getMoviesFromDatabase()
            _movies.postValue(databaseMovies)
        }
    }
}

// Extension function to convert GenreData to Genre entity
fun GenresListResponse.GenreData.toGenre(): Genre {
    return Genre(
        id = this.id,
        name = this.name
    )
}

object MovieModule {
    private lateinit var appDatabase: AppDatabase

    fun initialize(appDatabase: AppDatabase) {
        this.appDatabase = appDatabase
    }

    val lastMoviesRepository: LastMoviesRepository by lazy {
        LastMoviesRepository(API.baseUserService, appDatabase.moviesDao())
    }

    val genresListRepository: GenresListRepository by lazy {
        GenresListRepository(API.baseUserService)
    }
}

class HomeScreenViewModelFactory(private val appDatabase: AppDatabase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            MovieModule.initialize(appDatabase)
            return HomeScreenViewModel(
                MovieModule.lastMoviesRepository,
                MovieModule.genresListRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}




