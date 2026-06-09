package com.movietablecompose.ui.screen

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
import com.movietablecompose.domain.model.Movie
import com.movietablecompose.ui.viewmodel.MovieUiState

@Composable
fun ListScreen(
    uiState: MovieUiState,
    lastOpenedMovie: String?,
    onWebClick: (String) -> Unit,
    onDetailClick: (Movie) -> Unit,
    onRetryClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is MovieUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is MovieUiState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center).padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = uiState.message, textAlign = TextAlign.Center, color = Color.Red)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onRetryClick) {
                        Text("Coba Lagi")
                    }
                }
            }
            is MovieUiState.Success -> {
                val movies = uiState.movies
                val featured = movies.take(5)

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    if (!lastOpenedMovie.isNullOrEmpty()) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "Terakhir dibuka: $lastOpenedMovie",
                                    modifier = Modifier.padding(12.dp),
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                    item {
                        Text(
                            text = "Featured Movies",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    item {
                        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                            items(featured) { movie ->
                                FeaturedCard(movie = movie, onClick = { onDetailClick(movie) })
                            }
                        }
                    }

                    item {
                        Text(
                            text = "Popular Movies",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                        )
                    }

                    items(movies) { movie ->
                        MovieCard(
                            movie = movie,
                            onWebClick = { onWebClick("https://www.themoviedb.org/movie/${movie.id}") },
                            onDetailClick = { onDetailClick(movie) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturedCard(movie: Movie, onClick: () -> Unit) {
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
                model = movie.backdropUrl,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(colors = listOf(Color.Transparent, Color.Black), startY = 100f))
            )
            Text(
                text = movie.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.BottomStart).padding(12.dp)
            )
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    onWebClick: () -> Unit,
    onDetailClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 80.dp, height = 120.dp).clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = movie.title,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "⭐ %.1f".format(movie.voteAverage),
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray,
                        modifier = Modifier.background(Color.LightGray, RoundedCornerShape(4.dp)).padding(horizontal = 4.dp, vertical = 2.dp),
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Rilis: ${movie.releaseDate}", fontSize = 12.sp, color = Color.Gray)
                    Text(text = "Popular", fontSize = 12.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(16.dp))
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