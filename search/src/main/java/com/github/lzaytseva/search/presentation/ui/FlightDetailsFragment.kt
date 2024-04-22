package com.github.lzaytseva.search.presentation.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lzaytseva.search.R
import com.github.lzaytseva.search.databinding.FragmentFlightDetailsBinding
import com.github.lzaytseva.search.domain.model.TicketOffer
import com.github.lzaytseva.search.presentation.state.FlightDetailsScreenSideEffects
import com.github.lzaytseva.search.presentation.state.FlightDetailsScreenState
import com.github.lzaytseva.search.presentation.ui.adapter.TicketOfferAdapter
import com.github.lzaytseva.search.presentation.viewmodel.FlightDetailsViewModel
import com.github.lzaytseva.util.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar
import java.util.Locale

internal class FlightDetailsFragment :
    BaseFragment<FragmentFlightDetailsBinding, FlightDetailsViewModel>(
        FragmentFlightDetailsBinding::inflate
    ) {

    override val viewModel: FlightDetailsViewModel by viewModel()

    private var from = ""
    private var to = ""

    private val adapter = TicketOfferAdapter()

    private val calendar = Calendar.getInstance()
    private var currentDay = 0
    private var currentMonth = 0
    private var currentYear = 0
    private var departureDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        from = requireArguments().getString(KEY_DEPARTURE, "")
        to = requireArguments().getString(KEY_ARRIVAL, "")
    }

    override fun onConfigureViews() {
        setupRoute()
        initRecyclerView()
        initCurrentDate()
        setDateToDepartureBtn(
            year = currentYear,
            day = currentDay,
            month = currentMonth
        )
        setOnDepartureDateClickListener()
        setOnReturnDateClickListener()
        setOnSwapBtnClickListener()
        setOnClearBtnClickListener()
        setOnShowTicketsBtnClickListener()
    }

    override fun onSubscribe() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    updateUi(it)
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffects.collect {
                    handleSideEffects(it)
                }
            }
        }
    }

    private fun updateUi(state: FlightDetailsScreenState) {
        when (state) {
            is FlightDetailsScreenState.Content -> {
                showContent(state.ticketOffers)
            }

            FlightDetailsScreenState.Initial -> {
                // no-op
            }
        }
    }

    private fun showContent(offers: List<TicketOffer>) {
        adapter.submitList(offers)
    }

    private fun handleSideEffects(sideEffect: FlightDetailsScreenSideEffects) {
        when (sideEffect) {
            is FlightDetailsScreenSideEffects.Error -> {
                showError(sideEffect.message)
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun setupRoute() {
        binding.tvFrom.text = from
        binding.tvTo.text = to
    }

    private fun initRecyclerView() {
        binding.rvTicketOffers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTicketOffers.adapter = adapter
    }

    private fun initCurrentDate() {
        currentDay = calendar[Calendar.DAY_OF_MONTH]
        currentMonth = calendar[Calendar.MONTH]
        currentYear = calendar[Calendar.YEAR]
    }

    private fun setDateToDepartureBtn(year: Int, month: Int, day: Int) {
        val (dayMonth, weekDay) = getDisplayedDate(year, month, day)
        binding.tvDayMonth.text = dayMonth
        binding.tvDayOfWeek.text = weekDay
        val longDisplayMonth = calendar.getDisplayName(
            Calendar.MONTH,
            Calendar.LONG_FORMAT,
            Locale("ru")
        )
        departureDate = "$day $longDisplayMonth"
    }

    private fun setDateToReturnBtn(year: Int, month: Int, day: Int) {
        val (dayMonth, weekDay) = getDisplayedDate(year, month, day)
        binding.icAdd.isVisible = false
        binding.tvReturnDate.text = dayMonth
        binding.tvReturnDayOfWeek.isVisible = true
        binding.tvReturnDayOfWeek.text = weekDay
    }

    private fun getDisplayedDate(year: Int, month: Int, day: Int): Pair<String, String> {
        calendar.set(year, month, day)
        val displayedMonth = calendar.getDisplayName(
            Calendar.MONTH,
            Calendar.SHORT_FORMAT, Locale("ru")
        )?.substringBefore(".")
        val displayedDay = calendar[Calendar.DAY_OF_MONTH]
        val weekDay =
            calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT_FORMAT, Locale("ru"))
        return Pair("$displayedDay $displayedMonth", ", $weekDay")
    }

    private fun setOnDepartureDateClickListener() {
        binding.btnDepartureDate.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, p1, p2, p3 ->
                setDateToDepartureBtn(p1, p2, p3)
            }

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                dateSetListener,
                currentYear,
                currentMonth,
                currentDay
            )
            datePickerDialog.show()
        }
    }

    private fun setOnReturnDateClickListener() {
        binding.btnReturnDate.setOnClickListener {
            val dateSetListener = DatePickerDialog.OnDateSetListener { _, p1, p2, p3 ->
                setDateToReturnBtn(p1, p2, p3)
            }

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                dateSetListener,
                currentYear,
                currentMonth,
                currentDay
            )
            datePickerDialog.show()
        }
    }

    private fun setOnSwapBtnClickListener() {
        binding.btnSwap.setOnClickListener {
            val from = binding.tvFrom.text
            val to = binding.tvTo.text
            binding.tvFrom.text = to
            binding.tvTo.text = from
        }
    }

    private fun setOnClearBtnClickListener() {
        binding.btnClear.setOnClickListener {
            binding.tvTo.text = EMPTY_STRING
        }
    }

    private fun setOnShowTicketsBtnClickListener() {
        val route = "${binding.tvFrom.text}-${binding.tvTo.text}"
        binding.btnShowAllTickets.setOnClickListener {
            findNavController().navigate(
                R.id.action_flightDetailsFragment_to_ticketsFragment,
                TicketsFragment.createArgs(
                    route = route,
                    date = departureDate
                )
            )
        }
    }

    companion object {
        private const val EMPTY_STRING = ""

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