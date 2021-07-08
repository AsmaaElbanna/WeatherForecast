package com.happycomp.weatherforecast.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.happycomp.weatherforecast.databinding.FragmentFavoriteBinding
import com.happycomp.weatherforecast.model.adapters.FavoriteAdapter
import com.happycomp.weatherforecast.viewmodel.FavoriteVM

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteVM: FavoriteVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        favoriteVM = ViewModelProvider(requireActivity()).get(FavoriteVM::class.java)

        favoriteVM.favorites.observe(viewLifecycleOwner, {
            binding.recyclerViewFav.adapter = FavoriteAdapter(it)
        })

        return binding.root
    }
}