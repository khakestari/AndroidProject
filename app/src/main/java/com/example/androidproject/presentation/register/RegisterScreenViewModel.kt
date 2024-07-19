package com.example.androidproject.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidproject.data.network.API
import com.example.androidproject.data.response.RegisterResponse
import kotlinx.coroutines.launch
import com.example.androidproject.data.repository.RegisterRepository
import com.example.androidproject.data.request.RegisterRequest

class RegisterScreenViewModel(private val repository: RegisterRepository) : ViewModel() {

    //region Properties
    private val _registerResult = MutableLiveData<Result<RegisterResponse>>()
    val registerResult: LiveData<Result<RegisterResponse>> = _registerResult
    //endregion

    //region Methods
    fun register(user: RegisterRequest) {
        viewModelScope.launch {
            _registerResult.value = repository.register(user)
        }
    }
    //endregion
}


class RegisterModule {
    private fun provideRegisterRepository(): RegisterRepository {
        return RegisterRepository(API.baseUserService)
    }

    fun provideRegisterViewModelFactory(): RegisterScreenViewModelFactory {
        return RegisterScreenViewModelFactory(provideRegisterRepository())
    }
}

class RegisterScreenViewModelFactory(private val repository: RegisterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterScreenViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

