package com.happycomp.weatherforecast.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.happycomp.weatherforecast.databinding.BottomSheetTemperatureBinding

class TemperatureUnit : BottomSheetDialogFragment() {

    private lateinit var binding : BottomSheetTemperatureBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetTemperatureBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rbAutoC.setOnClickListener {
            Toast.makeText(requireContext(), "Auto", Toast.LENGTH_SHORT).show()
        }
    }
}