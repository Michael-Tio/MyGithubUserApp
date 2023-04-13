package com.michael.mygithubuserapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.michael.mygithubuserapp.R
import com.michael.mygithubuserapp.ViewModelFactory
import com.michael.mygithubuserapp.database.FavoriteUser
import com.michael.mygithubuserapp.databinding.ActivityDetailUserBinding
import com.michael.mygithubuserapp.ui.favorite.FavoriteViewModel

class DetailUserActivity : AppCompatActivity() {

    private var _binding: ActivityDetailUserBinding? = null
    private val binding get() = _binding!!

    private var imgurl:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.detailuser)

        var fav = false

        UNAME = intent.getStringExtra(USERNAME)
        if (UNAME != null) {
            getUserDetail(UNAME as String)

            val viewModelFactory = ViewModelFactory.getInstance(application)
            val favViewModel = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel(application)::class.java]
            val favUser: LiveData<Boolean> = favViewModel.checkFav(UNAME as String)
            favUser.observe(this){
                fav = if (it){
                    binding.fabFav.setImageResource(R.drawable.baseline_favorite_24)
                    true
                } else{
                    binding.fabFav.setImageResource(R.drawable.baseline_favorite_border_24)
                    false
                }
            }

        }

        val sectionsPagerAdapter = SectionPagerAdapter(this@DetailUserActivity)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        binding.fabFav.setOnClickListener{
            insertDelete(fav, binding.txtChosenUsername.text.toString(), imgurl)
        }
    }

    private fun insertDelete(fav:Boolean, uname:String?, imgurl:String?){
        val viewModelFactory = ViewModelFactory.getInstance(application)
        val favViewModel = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel(application)::class.java]

        val favoriteUser = FavoriteUser(uname.toString(), imgurl)
        if (fav){
            favViewModel.delete(favoriteUser)
            Toast.makeText(this@DetailUserActivity, "Removed From Favorite", Toast.LENGTH_SHORT).show()
        }
        else{
            favViewModel.insert(favoriteUser)
            Toast.makeText(this@DetailUserActivity, "Added to Favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUserDetail(uname: String){
        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        if (uname != ""){
            detailViewModel.getUserChosen(uname)
        }

        detailViewModel.user.observe(this) { user ->
            binding.txtChosenUsername.text = user.login
            val imageChosenUserUrl = user.avatarUrl
            imgurl = imageChosenUserUrl
            Glide.with(this)
                .load(imageChosenUserUrl)
                .apply(
                    RequestOptions
                        .circleCropTransform()
                )
                .into(binding.imgChosenProfile)
            binding.apply {
                txtChosenName.text = user.name
                txtFollowers.text = "${user.followers}"
                txtFollowings.text = "${user.following}"
            }
        }
        detailViewModel.isLoadingDetail.observe(this) {
            showLoading(it)
        }
    }


    private fun showLoading(state: Boolean) { binding.progressBarDetail.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        const val USERNAME:String = "USERNAME"
        var UNAME: String? = null

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }
}