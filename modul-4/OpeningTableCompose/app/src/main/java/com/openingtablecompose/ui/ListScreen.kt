package com.openingtablecompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.openingtablecompose.data.ChessOpening

@Composable
fun ListScreen(
    openings: List<ChessOpening>,
    onWebClick: (String) -> Unit,
    onDetailClick: (ChessOpening) -> Unit
) {
    val featuredOpenings = openings.filter { it.isFeatured }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Text(text = "Featured Openings", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(16.dp))
        }
        item {
            LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                items(featuredOpenings) { opening ->
                    FeaturedCard(opening = opening, onClick = { onDetailClick(opening) })
                }
            }
        }
        item {
            Text(text = "All Openings", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(16.dp))
        }
        items(openings) { opening ->
            OpeningCard(
                opening = opening,
                onWebClick = { onWebClick(opening.externalUrl) },
                onDetailClick = { onDetailClick(opening) }
            )
        }
    }
}

@Composable
fun FeaturedCard(opening: ChessOpening, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(140.dp)
            .padding(horizontal = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = opening.imageUrl,
                contentDescription = opening.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(colors = listOf(Color.Transparent, Color.Black), startY = 100f))
            )
            Text(
                text = opening.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )
        }
    }
}

@Composable
fun OpeningCard(
    opening: ChessOpening,
    onWebClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = opening.imageUrl,
                contentDescription = opening.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 80.dp, height = 100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = opening.name, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
                    Text(
                        text = opening.ecoCode,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray,
                        modifier = Modifier
                            .background(Color.LightGray, RoundedCornerShape(4.dp))
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = opening.category, fontSize = 12.sp, color = Color.Gray)
                    Text(text = "• ${opening.moves}", fontSize = 12.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f).padding(start = 8.dp), textAlign = TextAlign.End)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    OutlinedButton(onClick = onWebClick, modifier = Modifier.weight(1f).height(36.dp), contentPadding = PaddingValues(0.dp)) {
                        Text("Web", fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onDetailClick, modifier = Modifier.weight(1f).height(36.dp), contentPadding = PaddingValues(0.dp)) {
                        Text("Detail", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}