package com.github.lzaytseva.search.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.github.lzaytseva.search.domain.api.TicketsInteractor
import com.github.lzaytseva.search.presentation.state.TicketsScreenState
import com.github.lzaytseva.util.BaseViewModel
import com.github.lzaytseva.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class TicketsViewModel(
    private val ticketsInteractor: TicketsInteractor
) : BaseViewModel() {

    private val _state =
        MutableStateFlow<TicketsScreenState>(TicketsScreenState.Initial)
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {

            _state.value = TicketsScreenState.Content(
                tickets = when (val resource = ticketsInteractor.getTickets()) {
                    is Resource.Error -> {
                        emptyList()
                    }

                    is Resource.Success -> {
                        resource.data!!
                    }
                }
            )
        }
    }
}
