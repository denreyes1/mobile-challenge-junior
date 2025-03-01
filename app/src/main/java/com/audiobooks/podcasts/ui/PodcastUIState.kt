package com.denreyes.clink.views

import com.audiobooks.podcasts.model.Podcast

data class PodcastUIState (
    val isLoading: Boolean = false,
    val podcasts: List<Podcast> = emptyList(),
    val error: String? = null
)