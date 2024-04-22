package com.github.lzaytseva.search.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.lzaytseva.search.R

internal class PlaceholderFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backButton = requireActivity().findViewById<ImageView>(R.id.btn_back)
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}