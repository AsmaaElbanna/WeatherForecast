package com.happycomp.weatherforecast.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.happycomp.weatherforecast.databinding.BottomSheetTemperatureBinding
import com.happycomp.weatherforecast.databinding.BottomSheetWindBinding

class WindUnit : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetWindBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetWindBinding.inflate(inflater, container, false)

        binding.rbMile.setOnClickListener {
            Toast.makeText(requireContext(), "Mile", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}