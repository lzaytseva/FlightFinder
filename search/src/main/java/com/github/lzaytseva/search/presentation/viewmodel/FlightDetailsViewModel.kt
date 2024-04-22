package com.github.lzaytseva.search.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.github.lzaytseva.search.domain.api.FlightDetailsInteractor
import com.github.lzaytseva.search.presentation.state.FlightDetailsScreenSideEffects
import com.github.lzaytseva.search.presentation.state.FlightDetailsScreenState
import com.github.lzaytseva.search.presentation.state.SearchScreenState
import com.github.lzaytseva.util.BaseViewModel
import com.github.lzaytseva.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class FlightDetailsViewModel(
    private val flightDetailsInteractor: FlightDetailsInteractor
) : BaseViewModel() {

    private val _state =
        MutableStateFlow<FlightDetailsScreenState>(FlightDetailsScreenState.Initial)
    val state = _state.asStateFlow()

    private val _sideEffects = MutableSharedFlow<FlightDetailsScreenSideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val ticketOffers = when (val resource = flightDetailsInteractor.getTicketOffers()) {
                is Resource.Error -> {
                    _sideEffects.emit(
                        FlightDetailsScreenSideEffects.Error(
                            resource.error ?: "Что-то пошло не так..."
                        )
                    )
                    emptyList()
                }

                is Resource.Success -> resource.data
            }
            _state.value = FlightDetailsScreenState.Content(
                ticketOffers = ticketOffers!!
            )
        }
    }
}