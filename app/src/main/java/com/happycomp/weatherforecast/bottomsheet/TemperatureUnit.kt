package com.happycomp.weatherforecast.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.happycomp.weatherforecast.databinding.BottomSheetTemperatureBinding
import com.happycomp.weatherforecast.model.enums.Units
import com.happycomp.weatherforecast.util.Constants

class TemperatureUnit : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetTemperatureBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetTemperatureBinding.inflate(inflater, container, false)

        when (Constants.currentUnits.value) {
            Units.Metric -> binding.rbAutoC.isChecked = true
            Units.Standard -> binding.rbF.isChecked = true
            Units.Imperial -> binding.rbK.isChecked = true
        }

        binding.btnConfirm.setOnClickListener {
            this.dismiss()
            val selectedUnit = when (binding.rgTempUnit.checkedRadioButtonId) {
                binding.rbAutoC.id -> Units.Metric
                binding.rbF.id -> Units.Standard
                binding.rbK.id -> Units.Imperial
                else -> Units.Metric
            }

            if (Constants.currentUnits.value != selectedUnit) {
                Constants.saveUnit(requireContext(), selectedUnit.name)
            }
        }
        return binding.root
    }
}