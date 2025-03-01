package com.audiobooks.podcasts.ui

import com.audiobooks.podcasts.model.Podcast

/**
 * Data class representing the UI state for the podcast list.
 *
 * @param isLoading Boolean flag indicating if podcasts are being loaded.
 * @param podcasts List of podcasts.
 * @param error Error message if loading fails.
 */
data class PodcastUIState (
    val isLoading: Boolean = false,
    val podcasts: List<Podcast> = emptyList(),
    val error: String? = null
)