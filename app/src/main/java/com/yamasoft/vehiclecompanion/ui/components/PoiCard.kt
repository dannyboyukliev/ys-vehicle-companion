package com.yamasoft.vehiclecompanion.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yamasoft.vehiclecompanion.domain.model.Poi
import com.yamasoft.vehiclecompanion.ui.theme.VehicleCompanionTheme

@Composable
fun PoiCard(
    poi: Poi,
    onFavoriteButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // POI Image/Icon
            Card(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (!poi.imageUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = poi.imageUrl,
                            contentDescription = "POI Icon",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = "POI Icon",
                            modifier = Modifier
                                .size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // POI Information
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = poi.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                poi.category?.let { category ->
                    Text(
                        text = category,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                poi.rating?.let { rating ->
                    Text(
                        text = "Rating: $rating/5",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            IconButton(
                onClick = onFavoriteButtonClick
            ) {
                if (poi.isFavorite) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Remove ${poi.name} from favorites",
                        tint = Color.Red,
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite ${poi.name}",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

val dummyPoi = Poi(
    id = 1,
    name = "Shell Gas Station",
    url = "https://www.shell.com/motorist/shell-station-locator.html",
    category = "Gas Station",
    rating = 4,
    imageUrl = "https://example.com/shell-station.jpg",
    latitude = 37.7749,
    longitude = -122.4194,
    isFavorite = false
)
@Preview(showBackground = true)
@Composable
private fun PoiCardPreview() {
    VehicleCompanionTheme {
        PoiCard(poi = dummyPoi)
    }
}

@Preview(showBackground = true)
@Composable
private fun PoiCardFavoritedPreview() {
    VehicleCompanionTheme {
        val dummyFavoritedPoi = dummyPoi.copy(isFavorite = true)
        PoiCard(poi = dummyFavoritedPoi)
    }
}