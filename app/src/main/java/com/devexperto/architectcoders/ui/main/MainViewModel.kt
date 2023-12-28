package com.devexperto.architectcoders.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.devexperto.architectcoders.domain.GetPopularMoviesUseCase
import com.devexperto.architectcoders.domain.RequestPopularMoviesUseCase
import com.devexperto.architectcoders.model.Error
import com.devexperto.architectcoders.model.MoviesRepository
import com.devexperto.architectcoders.model.database.Movie
import com.devexperto.architectcoders.model.toError
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .catch { cause ->  _state.update { it.copy(error = cause.toError()) } }
                .collect { movies ->
                _state.update {  UiState(movies = movies) }
            }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            val error = requestPopularMoviesUseCase()
            _state.update { it.copy(error = error) }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val error: Error? = null
    )
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(requestPopularMoviesUseCase, getPopularMoviesUseCase) as T
    }
}