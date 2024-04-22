package com.github.lzaytseva.search.presentation.ui

import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.lzaytseva.uikit.R
import com.github.lzaytseva.search.databinding.FragmentSearchBinding
import com.github.lzaytseva.search.domain.model.ConcertOffer
import com.github.lzaytseva.search.domain.model.PlaceOffer
import com.github.lzaytseva.search.presentation.state.SearchScreenSideEffects
import com.github.lzaytseva.search.presentation.state.SearchScreenState
import com.github.lzaytseva.search.presentation.ui.adapter.ConcertOfferAdapter
import com.github.lzaytseva.search.presentation.ui.adapter.PlaceOfferAdapter
import com.github.lzaytseva.search.presentation.viewmodel.SearchViewModel
import com.github.lzaytseva.util.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(
    FragmentSearchBinding::inflate
) {
    override val viewModel: SearchViewModel by viewModel()

    private val concertsAdapter = ConcertOfferAdapter()
    private val placesAdapter = PlaceOfferAdapter {
        binding.etBsTo.setText(it)
    }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onConfigureViews() {
        initConcertsRecyclerView()
        initPopularPlacesRecyclerView()
        initBottomSheet()
        setOnDestinationClickListener()
        setOnClearBtnClickListener()
        setEditorActionListener()
        setOnTripSuggestionsClickListener()
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

    private fun updateUi(state: SearchScreenState) {
        when (state) {
            is SearchScreenState.Content -> {
                showContent(state)
            }

            SearchScreenState.Initial -> {
                // no-op
            }
        }
    }

    private fun showContent(content: SearchScreenState.Content) {
        binding.etFrom.setText(content.lastDeparturePlace)
        setConcertOffers(content.concertOffers)
        setDestinationOffers(content.destinationOffers)
    }

    private fun setConcertOffers(list: List<ConcertOffer>) {
        if (list.isEmpty()) {
            binding.tvHeaderConcerts.isVisible = false
        } else {
            binding.tvHeaderConcerts.isVisible = true
            concertsAdapter.submitList(list)
        }
    }

    private fun setDestinationOffers(list: List<PlaceOffer>) {
        if (list.isEmpty()) {
            binding.rvConcerts.isVisible = false
        } else {
            binding.rvConcerts.isVisible = true
            placesAdapter.submitList(list)
        }
    }

    private fun handleSideEffects(sideEffect: SearchScreenSideEffects) {
        when (sideEffect) {
            is SearchScreenSideEffects.Error -> {
                showError(sideEffect.message)
            }

            is SearchScreenSideEffects.OpenFlightDetailsScreen -> {
                // открыть след экран
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

    private fun initConcertsRecyclerView() {
        binding.rvConcerts.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvConcerts.adapter = concertsAdapter
    }

    private fun initPopularPlacesRecyclerView() {
        binding.rvPopularPlaces.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPopularPlaces.adapter = placesAdapter
    }

    private fun initBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun setOnDestinationClickListener() {
        binding.etTo.setOnClickListener {
            binding.etBsFrom.text = binding.etFrom.text
            binding.etFrom.clearFocus()
            hideKeyboard()
            showBottomView()
        }
    }

    private fun showBottomView() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setOnClearBtnClickListener() {
        binding.btnClear.setOnClickListener {
            binding.etBsTo.text?.clear()
        }
    }

    private fun hideKeyboard() {
        val keyboard =
            requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(
            binding.etFrom.windowToken, 0
        )
    }

    private fun setEditorActionListener() {
        binding.etBsFrom.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchTickets(
                    from = binding.etBsFrom.text.toString(),
                    to = binding.etBsTo.text.toString()
                )
            }
            false
        }
    }

    private fun setOnTripSuggestionsClickListener() {
        binding.ivComplicatedRoute.setOnClickListener {
            goToPlaceholderScreen()
        }
        binding.ivEverywhere.setOnClickListener {
            binding.etBsTo.setText(getString(R.string.everywhere))
        }
        binding.ivHoliday.setOnClickListener {
            goToPlaceholderScreen()
        }
        binding.ivHotTickets.setOnClickListener {
            goToPlaceholderScreen()
        }
    }

    private fun goToPlaceholderScreen() {
        findNavController().navigate(com.github.lzaytseva.search.R.id.action_flightDetailsFragment_to_placeholderFragment)
    }
}