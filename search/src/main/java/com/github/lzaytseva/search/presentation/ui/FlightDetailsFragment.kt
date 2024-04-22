package com.github.lzaytseva.search.presentation.ui

import android.os.Bundle
import com.github.lzaytseva.search.databinding.FragmentFlightDetailsBinding
import com.github.lzaytseva.search.presentation.viewmodel.FlightDetailsViewModel
import com.github.lzaytseva.util.BaseFragment

internal class FlightDetailsFragment :
    BaseFragment<FragmentFlightDetailsBinding, FlightDetailsViewModel>(
        FragmentFlightDetailsBinding::inflate
    ) {

    override val viewModel: FlightDetailsViewModel = FlightDetailsViewModel()

    private var from = ""
    private var to = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        from = requireArguments().getString(KEY_DEPARTURE, "")
        to = requireArguments().getString(KEY_ARRIVAL, "")
    }

    override fun onConfigureViews() {
        setupRoute()
    }

    override fun onSubscribe() {

    }

    private fun setupRoute() {
        binding.tvFrom.text = from
        binding.tvTo.text = to
    }

    companion object {
        private const val KEY_DEPARTURE = "key_from"
        private const val KEY_ARRIVAL = "key_to"

        fun createArgs(from: String, to: String): Bundle {
            return Bundle().apply {
                putString(KEY_DEPARTURE, from)
                putString(KEY_ARRIVAL, to)
            }
        }
    }
}