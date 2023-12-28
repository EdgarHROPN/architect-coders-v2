package com.devexperto.architectcoders.domain

import com.devexperto.architectcoders.model.MoviesRepository

class FindoMovieUseCase(private val repository: MoviesRepository) {
    operator fun invoke(id: Int) = repository.findById(id)
}