package com.happycomp.weatherforecast.view.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.gms.maps.model.LatLng
import com.happycomp.weatherforecast.databinding.FragmentFavoriteBinding
import com.happycomp.weatherforecast.model.adapters.FavoriteAdapter
import com.happycomp.weatherforecast.model.extra.SwipeToDelete
import com.happycomp.weatherforecast.model.interfaces.NetworkHandler
import com.happycomp.weatherforecast.model.interfaces.SwipeListener
import com.happycomp.weatherforecast.model.extra.Constants
import com.happycomp.weatherforecast.view.activity.MapsActivity
import com.happycomp.weatherforecast.viewmodel.FavoriteVM
import com.happycomp.weatherforecast.viewmodel.FavoriteVMFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment(), SwipeListener, NetworkHandler {
    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var assistedFactory: FavoriteVMFactory

    private val favoriteVM: FavoriteVM by viewModels {
        FavoriteVMFactory.Factory(assistedFactory, this)
    }

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    private val resultContractMap =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val intent = it.data
                if (intent != null) {
                    val location = intent.getParcelableExtra<LatLng>(MapsActivity.SELECTED_LOCATION)
                    if (location != null) {
                        favoriteVM.addNewFavorite(location.latitude, location.longitude)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "You Didn't Select Location!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        binding.recyclerViewFav.adapter = favoriteAdapter
        ItemTouchHelper(SwipeToDelete(this)).attachToRecyclerView(binding.recyclerViewFav)

        favoriteVM.favorites.observe(viewLifecycleOwner, {
            favoriteAdapter.submitList(it)
            if (!favoriteVM.isRefreshed) {
                Constants.currentUnits.observe(viewLifecycleOwner, {
                    favoriteVM.refresh()
                })
            }
        })

        binding.fabAdd.setOnClickListener {
            resultContractMap.launch(Intent(activity, MapsActivity::class.java))
        }

        return binding.root
    }

    override fun onItemSwipeToDelete(position: Int) {
        favoriteVM.deleteFavorite(favoriteAdapter.favoriteAt(position))
    }

    override fun onConnectionFailed() {
        super.onConnectionFailed()
        Toast.makeText(
            requireContext(),
            "Failed to Connect, Please Check Your Network!",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onErrorOccurred() {
        Toast.makeText(requireContext(), "Error Occurred, Please try Again!", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onSuccess() {
        Toast.makeText(requireContext(), "Success to Add!", Toast.LENGTH_SHORT).show()
    }
}