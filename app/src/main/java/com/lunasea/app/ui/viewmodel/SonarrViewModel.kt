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

package com.stack.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stack.app.data.model.SonarrSeries
import com.stack.app.data.model.SonarrQueue
import com.stack.app.data.model.SonarrSystemStatus
import com.stack.app.data.model.SonarrEpisode
import com.stack.app.data.repository.SonarrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
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

    private val _calendarState = MutableStateFlow<SonarrCalendarState>(SonarrCalendarState.Loading)
    val calendarState: StateFlow<SonarrCalendarState> = _calendarState.asStateFlow()

    init {
        loadSeries()
        loadQueue()
        loadSystemStatus()
        // Load calendar for the next 30 days by default
        val today = java.time.LocalDate.now()
        val nextMonth = today.plusDays(30)
        loadCalendar(today.toString(), nextMonth.toString())
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

    fun loadCalendar(start: String, end: String) {
        viewModelScope.launch {
            _calendarState.value = SonarrCalendarState.Loading
            sonarrRepository.getCalendar(start, end).collect { result ->
                _calendarState.value = when {
                    result.isSuccess -> SonarrCalendarState.Success(result.getOrNull() ?: emptyList())
                    result.isFailure -> SonarrCalendarState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                    else -> SonarrCalendarState.Loading
                }
            }
        }
    }

    fun refresh() {
        loadSeries()
        loadQueue()
        loadSystemStatus()
        val today = java.time.LocalDate.now()
        val nextMonth = today.plusDays(30)
        loadCalendar(today.toString(), nextMonth.toString())
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

sealed class SonarrCalendarState {
    object Loading : SonarrCalendarState()
    data class Success(val episodes: List<com.stack.app.data.model.SonarrEpisode>) : SonarrCalendarState()
    data class Error(val message: String) : SonarrCalendarState()
}