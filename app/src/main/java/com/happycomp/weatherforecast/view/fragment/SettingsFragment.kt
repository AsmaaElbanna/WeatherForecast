package com.happycomp.weatherforecast.view.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.happycomp.weatherforecast.databinding.FragmentSettingsBinding
import com.happycomp.weatherforecast.view.bottomsheet.TemperatureUnit

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var locationManager: LocationManager

    private val receiver: GpsStatusReceiver = GpsStatusReceiver()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        binding.gpsSwitch.isChecked =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        binding.gpsSwitch.setOnClickListener {
            val isChecked = binding.gpsSwitch.isChecked

            var message = "Your GPS seems to be disabled, do you want to enable it?"
            if (!isChecked)
                message = "Your GPS is currently enabled, do you want to disable it?"
            binding.gpsSwitch.isChecked = !binding.gpsSwitch.isChecked
            buildAlertMessageNoGps(message)

        }

        binding.tempUnitContainer.setOnClickListener {
            TemperatureUnit().show(requireActivity().supportFragmentManager, "Temp unit")
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(
            receiver,
            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().unregisterReceiver(receiver)
    }

    private fun buildAlertMessageNoGps(message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val alertDialog =
            builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
                .setNegativeButton(
                    "No"
                ) { dialog, _ -> dialog.cancel() }.create()

        alertDialog.show()
    }

    private inner class GpsStatusReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            binding.gpsSwitch.isChecked =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }
    }
}