package com.michael.mygithubuserapp.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.michael.mygithubuserapp.R
import com.michael.mygithubuserapp.ViewModelFactory
import com.michael.mygithubuserapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.favoriteuser)

        getFavList()
    }

    private fun showRecycleView(){
        binding.rvFav.apply{
            val layoutManager = LinearLayoutManager(this@FavoriteActivity)
            binding.rvFav.layoutManager = layoutManager

            val itemDecoration = DividerItemDecoration(this@FavoriteActivity, layoutManager.orientation)
            binding.rvFav.addItemDecoration(itemDecoration)

            setHasFixedSize(true)
        }
    }

    private fun getFavList(){
        showRecycleView()

        val viewModelFactory = ViewModelFactory.getInstance(application)
        val favViewModel = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel(application)::class.java]

        favViewModel.getAllFavUser().observe(this){
            binding.rvFav.adapter = FavoriteAdapter(it)
        }
    }
}