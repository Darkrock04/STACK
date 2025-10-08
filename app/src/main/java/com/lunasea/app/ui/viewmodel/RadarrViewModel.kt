package com.stack.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stack.app.data.model.RadarrMovie
import com.stack.app.data.model.RadarrQueue
import com.stack.app.data.model.RadarrSystemStatus
import com.stack.app.data.repository.RadarrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RadarrViewModel @Inject constructor(
    private val radarrRepository: RadarrRepository
) : ViewModel() {

    private val _moviesState = MutableStateFlow<RadarrMoviesState>(RadarrMoviesState.Loading)
    val moviesState: StateFlow<RadarrMoviesState> = _moviesState.asStateFlow()

    private val _queueState = MutableStateFlow<RadarrQueueState>(RadarrQueueState.Loading)
    val queueState: StateFlow<RadarrQueueState> = _queueState.asStateFlow()

    private val _systemState = MutableStateFlow<RadarrSystemState>(RadarrSystemState.Loading)
    val systemState: StateFlow<RadarrSystemState> = _systemState.asStateFlow()

    init {
        loadMovies()
        loadQueue()
        loadSystemStatus()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _moviesState.value = RadarrMoviesState.Loading
            radarrRepository.getMovies().collect { result ->
                _moviesState.value = when {
                    result.isSuccess -> RadarrMoviesState.Success(result.getOrNull() ?: emptyList())
                    result.isFailure -> RadarrMoviesState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                    else -> RadarrMoviesState.Loading
                }
            }
        }
    }

    fun loadQueue() {
        viewModelScope.launch {
            _queueState.value = RadarrQueueState.Loading
            radarrRepository.getQueue().collect { result ->
                _queueState.value = when {
                    result.isSuccess -> RadarrQueueState.Success(result.getOrNull()?.records ?: emptyList())
                    result.isFailure -> RadarrQueueState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                    else -> RadarrQueueState.Loading
                }
            }
        }
    }

    fun loadSystemStatus() {
        viewModelScope.launch {
            _systemState.value = RadarrSystemState.Loading
            radarrRepository.getSystemStatus().collect { result ->
                _systemState.value = when {
                    result.isSuccess -> RadarrSystemState.Success(result.getOrNull())
                    result.isFailure -> RadarrSystemState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                    else -> RadarrSystemState.Loading
                }
            }
        }
    }

    fun refresh() {
        loadMovies()
        loadQueue()
        loadSystemStatus()
    }
}

sealed class RadarrMoviesState {
    object Loading : RadarrMoviesState()
    data class Success(val movies: List<RadarrMovie>) : RadarrMoviesState()
    data class Error(val message: String) : RadarrMoviesState()
}

sealed class RadarrQueueState {
    object Loading : RadarrQueueState()
    data class Success(val queue: List<RadarrQueue>) : RadarrQueueState()
    data class Error(val message: String) : RadarrQueueState()
}

sealed class RadarrSystemState {
    object Loading : RadarrSystemState()
    data class Success(val status: RadarrSystemStatus?) : RadarrSystemState()
    data class Error(val message: String) : RadarrSystemState()
} 