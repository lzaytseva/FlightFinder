package com.github.lzaytseva.search.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.github.lzaytseva.search.domain.api.SearchInteractor
import com.github.lzaytseva.search.presentation.state.SearchScreenSideEffects
import com.github.lzaytseva.search.presentation.state.SearchScreenState
import com.github.lzaytseva.util.BaseViewModel
import com.github.lzaytseva.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : BaseViewModel() {

    private val _state = MutableStateFlow<SearchScreenState>(SearchScreenState.Initial)
    val state = _state.asStateFlow()

    private val _sideEffects = MutableSharedFlow<SearchScreenSideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val lastDeparturePlace = searchInteractor.getLastDeparturePlace().orEmpty()
            val destinationOffers = searchInteractor.getDestinationRecommendations()
            val concertOffers = when (val resource = searchInteractor.getConcertOffers()) {
                is Resource.Error -> {
                    emptyList()
                }

                is Resource.Success -> resource.data
            }
            _state.value = SearchScreenState.Content(
                lastDeparturePlace = lastDeparturePlace,
                concertOffers = concertOffers!!,
                destinationOffers = destinationOffers
            )
        }
    }

    fun searchTickets(from: String, to: String) {
        // Сохраняем страну отправления до перехода на след экран
        searchInteractor.saveLastDeparturePlace(from)
        viewModelScope.launch {
            _sideEffects.emit(SearchScreenSideEffects.OpenFlightDetailsScreen(from, to))
        }
    }
}