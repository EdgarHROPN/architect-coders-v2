package com.devexperto.architectcoders.domain

import com.devexperto.architectcoders.model.Error
import com.devexperto.architectcoders.model.MoviesRepository

class RequestPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Error? = moviesRepository.requestPopularMovies()
}