package com.movietablecompose.data.mapper

import com.movietablecompose.data.model.dto.MovieDto
import com.movietablecompose.data.model.entity.MovieEntity
import com.movietablecompose.domain.model.Movie

fun MovieDto.toDomain(): Movie {
    val baseImageUrl = "https://image.tmdb.org/t/p/w500"
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = this.posterPath?.let { "$baseImageUrl$it" } ?: "",
        backdropUrl = this.backdropPath?.let { "$baseImageUrl$it" } ?: "",
        releaseDate = this.releaseDate ?: "",
        voteAverage = this.voteAverage
    )
}

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = this.posterUrl,
        backdropUrl = this.backdropUrl,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage
    )
}

fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = this.posterUrl,
        backdropUrl = this.backdropUrl,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage
    )
}