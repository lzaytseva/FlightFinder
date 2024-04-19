package com.github.lzaytseva.search.presentation.ui

import com.github.lzaytseva.search.databinding.FragmentSearchBinding
import com.github.lzaytseva.search.presentation.viewmodel.SearchViewModel
import com.github.lzaytseva.util.BaseFragment

internal class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(
    FragmentSearchBinding::inflate
) {
    override val viewModel: SearchViewModel = SearchViewModel()

    override fun onConfigureViews() {

    }

    override fun onSubscribe() {

    }
}