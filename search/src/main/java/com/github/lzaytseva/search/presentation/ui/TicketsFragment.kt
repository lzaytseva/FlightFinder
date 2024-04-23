package com.github.lzaytseva.search.presentation.ui

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lzaytseva.search.databinding.FragmentTicketsBinding
import com.github.lzaytseva.search.domain.model.Ticket
import com.github.lzaytseva.search.presentation.state.TicketsScreenState
import com.github.lzaytseva.search.presentation.ui.adapter.TicketAdapter
import com.github.lzaytseva.search.presentation.viewmodel.TicketsViewModel
import com.github.lzaytseva.uikit.R
import com.github.lzaytseva.util.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class TicketsFragment : BaseFragment<FragmentTicketsBinding, TicketsViewModel>(
    FragmentTicketsBinding::inflate
) {
    override val viewModel: TicketsViewModel by viewModel()

    private val adapter = TicketAdapter()

    private var date = ""
    private var route = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        date = requireArguments().getString(ARG_DATE, "")
        route = requireArguments().getString(ARG_ROUTE, "")
    }

    override fun onConfigureViews() {
        setFlightInfo()
        initRecyclerView()
        setBtnBackClickListener()
    }

    override fun onSubscribe() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    updateUi(it)
                }
            }
        }
    }

    private fun updateUi(state: TicketsScreenState) {
        when (state) {
            is TicketsScreenState.Content -> {
                showContent(state.tickets)
            }

            TicketsScreenState.Initial -> {
                // no-op
            }
        }
    }

    private fun showContent(tickets: List<Ticket>) {
        if (tickets.isEmpty()) {
            binding.rvTickets.isVisible = false
        } else {
            binding.rvTickets.isVisible = true
            adapter.submitList(tickets)
        }
    }

    private fun setFlightInfo() {
        binding.tvRoute.text = route
        binding.tvExtraInfo.text = getString(R.string.date_passengers, date)
    }

    private fun initRecyclerView() {
        binding.rvTickets.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTickets.adapter = adapter
    }

    private fun setBtnBackClickListener() {
        binding.icBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    companion object {
        private const val ARG_ROUTE = "arg_route"
        private const val ARG_DATE = "arg_date"

        fun createArgs(route: String, date: String): Bundle {
            return Bundle().apply {
                putString(ARG_ROUTE, route)
                putString(ARG_DATE, date)
            }
        }
    }
}