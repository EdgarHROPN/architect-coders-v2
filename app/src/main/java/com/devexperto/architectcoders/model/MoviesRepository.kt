package com.devexperto.architectcoders.model

import com.devexperto.architectcoders.R
import com.devexperto.architectcoders.model.database.Movie
import com.devexperto.architectcoders.model.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.model.datasource.MovieRemoteDataSource
import com.devexperto.architectcoders.ui.App

class MoviesRepository(application: App) {

    private val regionRepository = RegionRepository(application)
    private val localDataSource = MovieLocalDataSource(application.db.movieDao())
    private val remoteDataSource = MovieRemoteDataSource(
        application.getString(R.string.api_key)
    )

    val popularMovies = localDataSource.movies

    suspend fun requestPopularMovies(){
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            localDataSource.save(movies.results.map { it.toLocalModel() })
        }
    }
}

private fun RemoteMovie.toLocalModel(): Movie = Movie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath ?: "",
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage
)