package com.github.lzaytseva.search.presentation.ui

import com.github.lzaytseva.search.databinding.FragmentFlightDetailsBinding
import com.github.lzaytseva.search.presentation.viewmodel.FlightDetailsViewModel
import com.github.lzaytseva.util.BaseFragment

internal class FlightDetailsFragment :
    BaseFragment<FragmentFlightDetailsBinding, FlightDetailsViewModel>(
        FragmentFlightDetailsBinding::inflate
    ) {
    override val viewModel: FlightDetailsViewModel = FlightDetailsViewModel()

    override fun onConfigureViews() {

    }

    override fun onSubscribe() {

    }
}