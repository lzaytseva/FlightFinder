package com.github.lzaytseva.search.presentation.ui

import android.os.Bundle
import android.view.View
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
import com.github.lzaytseva.search.R
import com.github.lzaytseva.search.databinding.FragmentSearchBinding
import com.github.lzaytseva.search.domain.model.ConcertOffer
import com.github.lzaytseva.search.domain.model.PlaceOffer
import com.github.lzaytseva.search.presentation.state.SearchScreenSideEffects
import com.github.lzaytseva.search.presentation.state.SearchScreenState
import com.github.lzaytseva.search.presentation.ui.adapter.ConcertOfferAdapter
import com.github.lzaytseva.search.presentation.ui.adapter.PlaceOfferAdapter
import com.github.lzaytseva.search.presentation.ui.util.CyrillicInputFilter
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
    private var bottomSheetWasOpened = false

    private val editTextFilter = CyrillicInputFilter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (bottomSheetWasOpened) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onConfigureViews() {
        setUpEditTextFilters()
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
            is SearchScreenSideEffects.OpenFlightDetailsScreen -> {
                findNavController().navigate(
                    R.id.action_searchFragment_to_flightDetailsFragment,
                    FlightDetailsFragment.createArgs(sideEffect.from, sideEffect.to)
                )
            }
        }
    }

    private fun setUpEditTextFilters() {
        binding.etFrom.filters = arrayOf(editTextFilter)
        binding.etBsFrom.filters = arrayOf(editTextFilter)
        binding.etBsTo.filters = arrayOf(editTextFilter)
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
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(p0: View, p1: Int) {
                if (p1 == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetWasOpened = true
                } else if (p1 == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetWasOpened = false
                }
            }

            override fun onSlide(p0: View, p1: Float) {}

        })
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
        binding.etBsTo.setOnEditorActionListener { v, actionId, event ->
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
            binding.etBsTo.setText(getString(com.github.lzaytseva.uikit.R.string.everywhere))
        }
        binding.ivHoliday.setOnClickListener {
            goToPlaceholderScreen()
        }
        binding.ivHotTickets.setOnClickListener {
            goToPlaceholderScreen()
        }
    }

    private fun goToPlaceholderScreen() {
        findNavController().navigate(R.id.action_searchFragment_to_placeholderFragment)
    }
}