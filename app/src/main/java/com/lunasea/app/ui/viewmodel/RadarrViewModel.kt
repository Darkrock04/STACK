    val queueState: StateFlow<RadarrQueueState> = _queueState.asStateFlow()

    private val _systemState = MutableStateFlow<RadarrSystemState>(RadarrSystemState.Loading)
    val systemState: StateFlow<RadarrSystemState> = _systemState.asStateFlow()

    private val _calendarState = MutableStateFlow<RadarrCalendarState>(RadarrCalendarState.Loading)
    val calendarState: StateFlow<RadarrCalendarState> = _calendarState.asStateFlow()

    init {
        loadMovies()
        loadQueue()
        loadSystemStatus()
        // Load calendar for the next 30 days by default
        val today = java.time.LocalDate.now()
        val nextMonth = today.plusDays(30)
        loadCalendar(today.toString(), nextMonth.toString())
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

    fun loadCalendar(start: String, end: String) {
        viewModelScope.launch {
            _calendarState.value = RadarrCalendarState.Loading
            radarrRepository.getCalendar(start, end).collect { result ->
                _calendarState.value = when {
                    result.isSuccess -> RadarrCalendarState.Success(result.getOrNull() ?: emptyList())
                    result.isFailure -> RadarrCalendarState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                    else -> RadarrCalendarState.Loading
                }
            }
        }
    }

    fun refresh() {
        loadMovies()
        loadQueue()
        loadSystemStatus()
        val today = java.time.LocalDate.now()
        val nextMonth = today.plusDays(30)
        loadCalendar(today.toString(), nextMonth.toString())
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

sealed class RadarrCalendarState {
    object Loading : RadarrCalendarState()
    data class Success(val movies: List<RadarrMovie>) : RadarrCalendarState()
    data class Error(val message: String) : RadarrCalendarState()
}