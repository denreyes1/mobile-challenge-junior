package com.audiobooks.podcasts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.audiobooks.podcasts.network.PodcastRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel to manage podcast list state and retrieve data from the repository.
 */
class PodcastListViewModel : ViewModel() {
    val podcastUIState = MutableStateFlow(PodcastUIState())
    private val repository = PodcastRepository()

    init {
        getPodcasts()
    }

    /**
     * Fetches podcasts from the repository and updates UI state accordingly.
     */
    private fun getPodcasts() {
        podcastUIState.value = PodcastUIState(isLoading = true)
        viewModelScope.launch {
            val result = repository.getPodcasts()
            result.onSuccess { podcasts ->
                podcastUIState.update {
                    it.copy(isLoading = false, podcasts = podcasts)
                }
            }.onFailure { exception ->
                podcastUIState.update {
                    it.copy(isLoading = false, error = exception.message ?: "Unknown error")
                }
            }
        }
    }
}
