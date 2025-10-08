package com.stack.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stack.app.data.model.SonarrSeries
import com.stack.app.data.model.SonarrQueue
import com.stack.app.data.model.SonarrSystemStatus
import com.stack.app.data.repository.SonarrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SonarrViewModel @Inject constructor(
    private val sonarrRepository: SonarrRepository
) : ViewModel() {

    private val _seriesState = MutableStateFlow<SonarrSeriesState>(SonarrSeriesState.Loading)
    val seriesState: StateFlow<SonarrSeriesState> = _seriesState.asStateFlow()

    private val _queueState = MutableStateFlow<SonarrQueueState>(SonarrQueueState.Loading)
    val queueState: StateFlow<SonarrQueueState> = _queueState.asStateFlow()

    private val _systemState = MutableStateFlow<SonarrSystemState>(SonarrSystemState.Loading)
    val systemState: StateFlow<SonarrSystemState> = _systemState.asStateFlow()

    init {
        loadSeries()
        loadQueue()
        loadSystemStatus()
    }

    fun loadSeries() {
        viewModelScope.launch {
            _seriesState.value = SonarrSeriesState.Loading
            sonarrRepository.getSeries().collect { result ->
                _seriesState.value = when {
                    result.isSuccess -> SonarrSeriesState.Success(result.getOrNull() ?: emptyList())
                    result.isFailure -> SonarrSeriesState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                    else -> SonarrSeriesState.Loading
                }
            }
        }
    }

    fun loadQueue() {
        viewModelScope.launch {
            _queueState.value = SonarrQueueState.Loading
            sonarrRepository.getQueue().collect { result ->
                _queueState.value = when {
                    result.isSuccess -> SonarrQueueState.Success(result.getOrNull()?.records ?: emptyList())
                    result.isFailure -> SonarrQueueState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                    else -> SonarrQueueState.Loading
                }
            }
        }
    }

    fun loadSystemStatus() {
        viewModelScope.launch {
            _systemState.value = SonarrSystemState.Loading
            sonarrRepository.getSystemStatus().collect { result ->
                _systemState.value = when {
                    result.isSuccess -> SonarrSystemState.Success(result.getOrNull())
                    result.isFailure -> SonarrSystemState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                    else -> SonarrSystemState.Loading
                }
            }
        }
    }

    fun refresh() {
        loadSeries()
        loadQueue()
        loadSystemStatus()
    }
}

sealed class SonarrSeriesState {
    object Loading : SonarrSeriesState()
    data class Success(val series: List<SonarrSeries>) : SonarrSeriesState()
    data class Error(val message: String) : SonarrSeriesState()
}

sealed class SonarrQueueState {
    object Loading : SonarrQueueState()
    data class Success(val queue: List<SonarrQueue>) : SonarrQueueState()
    data class Error(val message: String) : SonarrQueueState()
}

sealed class SonarrSystemState {
    object Loading : SonarrSystemState()
    data class Success(val status: SonarrSystemStatus?) : SonarrSystemState()
    data class Error(val message: String) : SonarrSystemState()
} 