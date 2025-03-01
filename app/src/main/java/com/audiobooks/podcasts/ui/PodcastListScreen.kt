package com.audiobooks.podcasts.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.audiobooks.podcasts.R
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.ui.theme.PodcastsTheme

/**
 * Displays a list of podcasts and handles UI states such as loading and errors.
 *
 * @param onShowDetails Callback to handle navigation to the details screen.
 */
@Composable
fun PodcastListScreen(onShowDetails: (podcast: Podcast) -> Unit) {
    val viewModel: PodcastListViewModel = viewModel()
    val podcastUIState by viewModel.podcastUIState.collectAsStateWithLifecycle()

    Column {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(
                horizontal = 24.dp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedVisibility(
            visible = podcastUIState.isLoading,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        AnimatedVisibility(
            visible = podcastUIState.podcasts.isNotEmpty(),
            enter = fadeIn(animationSpec = tween(durationMillis = 2000))
        ) {
            PodcastListUI(
                podcastUIState.podcasts,
                onShowDetails = onShowDetails
            )
        }
        AnimatedVisibility(
            visible = podcastUIState.error != null,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = podcastUIState.error ?: "")
            }
        }

    }

}

@Composable
private fun PodcastListUI(
    podcasts: List<Podcast>,
    onShowDetails: (podcast: Podcast) -> Unit
) {
    LazyColumn {
        items(podcasts) { podcast ->
            PodcastItemView(podcast, onShowDetails)
        }
    }
}

@Composable
fun PodcastItemView(
    podcast: Podcast,
    onShowDetails: (podcast: Podcast) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onShowDetails(podcast) },
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                    horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = podcast.image,
                contentDescription = podcast.title,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = podcast.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = podcast.publisher,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

        }

        HorizontalDivider(
            thickness = 0.4.dp,
            color = Color.LightGray,
            modifier = Modifier.fillMaxWidth()
                .padding(
                    start = 24.dp
                ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PodcastListUIPreview() {
    val samplePodcast  = Podcast(
        title = "Example Podcast Title",
        description="A little show about big ideas. From the people who make Planet Money, The Indicator helps you make sense of what's happening today. It's a quick hit of insight into work, business, the economy, and everything else. Listen weekday afternoons. Got money on your mind? Try Planet Money+ - a new way to support the show you love, get a sponsor-free feed of the podcast, *and* get access to bonus content. A subscription also gets you access to The Indicator and Planet Money Summer School, both without interruptions.",
        id="abc",
        image="https://cdn-images-3.listennotes.com/podcasts/the-ed-mylett-show-ed-mylett-cumulus-guxpvEVnHTJ-PEUIT9RBhZD.1400x1400.jpg",
        publisher="Podcast Publisher"
    )
    val podcasts = List(5) { samplePodcast }

    PodcastsTheme {
        PodcastListUI(
            podcasts,
            onShowDetails = {}
        )
    }
}
