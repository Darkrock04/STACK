import com.stack.app.ui.viewmodel.RadarrViewModel
import com.stack.app.ui.viewmodel.RadarrMoviesState
import com.stack.app.ui.viewmodel.RadarrQueueState
import com.stack.app.ui.viewmodel.RadarrSystemState
import com.stack.app.ui.viewmodel.RadarrCalendarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadarrScreen(navController: NavController) {
    val viewModel: RadarrViewModel = hiltViewModel()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.movies),
        stringResource(R.string.queue),
        stringResource(R.string.calendar),
        stringResource(R.string.wanted),
        stringResource(R.string.system)
    )
    
    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) },
                    icon = {
                        Icon(
                            imageVector = when (index) {
                                0 -> Icons.Filled.Star
                                1 -> Icons.Filled.Download
                                2 -> Icons.Filled.DateRange
                                3 -> Icons.Filled.Search
                                4 -> Icons.Filled.Settings
                                else -> Icons.Filled.Star
                            },
                            contentDescription = null
                        )
                    }
                )
            }
        }
        
        when (selectedTabIndex) {
            0 -> RadarrMoviesTab(viewModel)
            1 -> RadarrQueueTab(viewModel)
            2 -> RadarrCalendarTab(viewModel)
            3 -> RadarrWantedTab()
            4 -> RadarrSystemTab(viewModel)
        }
    }
}

@Composable
fun RadarrMoviesTab(viewModel: RadarrViewModel) {
    val moviesState by viewModel.moviesState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.movies),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        when (moviesState) {
            is RadarrMoviesState.Loading -> {
                CircularProgressIndicator()
                Text(
                    text = stringResource(R.string.loading),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is RadarrMoviesState.Success -> {
                val movies = (moviesState as RadarrMoviesState.Success).movies
                if (movies.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_data),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        text = "Found ${movies.size} movies",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            is RadarrMoviesState.Error -> {
                Text(
                    text = (moviesState as RadarrMoviesState.Error).message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun RadarrQueueTab(viewModel: RadarrViewModel) {
    val queueState by viewModel.queueState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.queue),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        when (queueState) {
            is RadarrQueueState.Loading -> {
                CircularProgressIndicator()
                Text(
                    text = stringResource(R.string.loading),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is RadarrQueueState.Success -> {
                val queue = (queueState as RadarrQueueState.Success).queue
                if (queue.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_data),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    androidx.compose.foundation.lazy.LazyColumn {
                        items(queue.size) { index ->
                            val item = queue[index]
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = item.title,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Status: ${item.status}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    if (item.timeleft != null) {
                                        Text(
                                            text = "Time left: ${item.timeleft}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                    val progress = if (item.size > 0) 1f - (item.sizeleft.toFloat() / item.size.toFloat()) else 0f
                                    LinearProgressIndicator(
                                        progress = progress,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is RadarrQueueState.Error -> {
                Text(
                    text = (queueState as RadarrQueueState.Error).message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun RadarrCalendarTab(viewModel: RadarrViewModel) {
    val calendarState by viewModel.calendarState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.calendar),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        when (calendarState) {
            is RadarrCalendarState.Loading -> {
                CircularProgressIndicator()
                Text(
                    text = stringResource(R.string.loading),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is RadarrCalendarState.Success -> {
                val movies = (calendarState as RadarrCalendarState.Success).movies
                if (movies.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_data),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    androidx.compose.foundation.lazy.LazyColumn {
                        items(movies.size) { index ->
                            val movie = movies[index]
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = movie.title,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Release: ${movie.inCinemas ?: movie.digitalRelease ?: movie.physicalRelease ?: "Unknown"}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    if (movie.hasFile) {
                                        Text(
                                            text = "Downloaded",
                                            color = MaterialTheme.colorScheme.primary,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    } else {
                                        Text(
                                            text = "Missing",
                                            color = MaterialTheme.colorScheme.error,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            is RadarrCalendarState.Error -> {
                Text(
                    text = (calendarState as RadarrCalendarState.Error).message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun RadarrWantedTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.wanted),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.loading),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun RadarrSystemTab(viewModel: RadarrViewModel) {
    val systemState by viewModel.systemState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.system),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        when (systemState) {
            is RadarrSystemState.Loading -> {
                CircularProgressIndicator()
                Text(
                    text = stringResource(R.string.loading),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is RadarrSystemState.Success -> {
                val status = (systemState as RadarrSystemState.Success).status
                if (status != null) {
                    Text(
                        text = "Version: ${status.version}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "OS: ${status.osName}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    Text(
                        text = stringResource(R.string.no_data),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            is RadarrSystemState.Error -> {
                Text(
                    text = (systemState as RadarrSystemState.Error).message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}