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
import com.happycomp.weatherforecast.databinding.FragmentFavoriteBinding
import com.happycomp.weatherforecast.model.adapters.FavoriteAdapter
import com.happycomp.weatherforecast.model.adapters.SwipeToDelete
import com.happycomp.weatherforecast.model.interfaces.SwipeListener
import com.happycomp.weatherforecast.view.activity.MapsActivity
import com.happycomp.weatherforecast.viewmodel.FavoriteVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment(), SwipeListener {
    private lateinit var binding: FragmentFavoriteBinding
    private val favoriteVM: FavoriteVM by viewModels()
    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    private val resultContractMap =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val intent = it.data
                if (intent != null) {
                    val lat = intent.getDoubleExtra(MapsActivity.LATITUDE, -1.0)
                    val long = intent.getDoubleExtra(MapsActivity.LONGITUDE, -1.0)
                    if (lat != -1.0 || long != -1.0) {
                        Toast.makeText(requireContext(), "$lat, $long", Toast.LENGTH_SHORT).show()
                        favoriteAdapter.currentList.find { favorite ->
                            favorite.lat == lat && favorite.lon == long
                        }.also { result ->
                            if(result != null){
                                Toast.makeText(requireContext(), "Here!", Toast.LENGTH_SHORT).show()
                            } else{
                                favoriteVM.addNewFavorite(lat, long)
                            }
                        }
                    }
                    else{
                        Toast.makeText(requireContext(), "You Didn't Select Location!", Toast.LENGTH_SHORT).show()
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
        })

        binding.fabAdd.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            resultContractMap.launch(intent)
        }

        return binding.root
    }

    override fun onItemSwipeToDelete(position: Int) {
        favoriteVM.deleteFavorite(favoriteAdapter.favoriteAt(position))
    }

}