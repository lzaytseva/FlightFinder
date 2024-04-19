package com.github.lzaytseva.search.presentation.ui

import com.github.lzaytseva.search.databinding.FragmentTicketsBinding
import com.github.lzaytseva.search.presentation.viewmodel.TicketsViewModel
import com.github.lzaytseva.util.BaseFragment

internal class TicketsFragment : BaseFragment<FragmentTicketsBinding, TicketsViewModel>(
    FragmentTicketsBinding::inflate
) {
    override val viewModel: TicketsViewModel = TicketsViewModel()

    override fun onConfigureViews() {
    }

    override fun onSubscribe() {

    }
}