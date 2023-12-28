package com.devexperto.architectcoders.domain

import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.model.database.Movie
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {
    operator fun invoke(): Flow<List<Movie>> = repository.popularMovies
}