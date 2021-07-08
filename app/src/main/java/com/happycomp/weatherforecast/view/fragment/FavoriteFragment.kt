package com.happycomp.weatherforecast.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.happycomp.weatherforecast.databinding.FragmentFavoriteBinding
import com.happycomp.weatherforecast.model.adapters.FavoriteAdapter
import com.happycomp.weatherforecast.model.adapters.SwipeToDelete
import com.happycomp.weatherforecast.model.interfaces.SwipeListener
import com.happycomp.weatherforecast.view.activity.MapsActivity
import com.happycomp.weatherforecast.viewmodel.FavoriteVM

class FavoriteFragment : Fragment(), SwipeListener {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteVM: FavoriteVM
    private lateinit var favoriteAdapter: FavoriteAdapter

    private lateinit var mapsResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        favoriteVM = ViewModelProvider(requireActivity()).get(FavoriteVM::class.java)
        binding.recyclerViewFav.adapter = FavoriteAdapter().also { this.favoriteAdapter = it }
        ItemTouchHelper(SwipeToDelete(this)).attachToRecyclerView(binding.recyclerViewFav)

        favoriteVM.favorites.observe(viewLifecycleOwner, {
            favoriteAdapter.submitList(it)
        })

        binding.fabAdd.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onItemSwipeToDelete(position: Int) {
        favoriteVM.deleteFavorite(favoriteAdapter.favoriteAt(position))
    }
}