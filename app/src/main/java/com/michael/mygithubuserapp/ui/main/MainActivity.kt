package com.michael.mygithubuserapp.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.michael.mygithubuserapp.R
import com.michael.mygithubuserapp.databinding.ActivityMainBinding
import com.michael.mygithubuserapp.ui.favorite.FavoriteActivity
import com.michael.mygithubuserapp.ui.settings.SettingActivity
import com.michael.mygithubuserapp.ui.settings.SettingPreferences
import com.michael.mygithubuserapp.ui.settings.SettingViewModel
import com.michael.mygithubuserapp.ui.settings.SettingViewModelFactory

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var username:String = ""
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.github_users_search)

        getUserList(username)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun showRecycleView(){
        binding.rvUsers.apply{
            val layoutManager = LinearLayoutManager(this@MainActivity)
            binding.rvUsers.layoutManager = layoutManager

            val itemDecoration = DividerItemDecoration(this@MainActivity, layoutManager.orientation)
            binding.rvUsers.addItemDecoration(itemDecoration)

            setHasFixedSize(true)
        }
    }

    private fun getUserList(uname: String){
        showRecycleView()

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        if (uname != ""){
            mainViewModel.getUsers(uname)
        }
        mainViewModel.user.observe(this) { user ->
            binding.rvUsers.adapter = ListUserAdapter(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getUserList(query)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favpage -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.setting ->{
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}