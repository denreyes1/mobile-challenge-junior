package com.audiobooks.podcasts.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.audiobooks.podcasts.model.Podcast
import com.audiobooks.podcasts.ui.theme.PodcastsTheme

@Composable
fun PodcastListScreen(onShowDetails: (podcast: Podcast) -> Unit) {
    // TODO - Implement the ViewModel to fetch the list of podcasts and update the UI
    // TODO - Modify this file as needed
    // TODO - Coil dependency was added as the image loader for the podcast image - feel free to use any other image loader
    val viewModel: PodcastListViewModel = viewModel()

    PodcastListUI(
        onShowDetails = onShowDetails
    )

}

@Composable
private fun PodcastListUI(
    onShowDetails: (podcast: Podcast) -> Unit
) {
    // Mock Data, replace to API data when implemented
    val podcasts = listOf(
        Podcast("1", "The Indicator from Planet Money",
            "Description",
            "https://cdn-images-3.listennotes.com/podcasts/the-ed-mylett-show-ed-mylett-cumulus-guxpvEVnHTJ-PEUIT9RBhZD.1400x1400.jpg",
            "NPR"),
        Podcast("2", "WorkLife with Adam Grant",
            "Description",
            "https://cdn-images-3.listennotes.com/podcasts/the-ed-mylett-show-ed-mylett-cumulus-guxpvEVnHTJ-PEUIT9RBhZD.1400x1400.jpg",
            "Ted"),
    )

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
    PodcastsTheme {
        PodcastListUI(
            onShowDetails = {}
        )
    }
}
