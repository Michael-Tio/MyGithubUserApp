package com.michael.mygithubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.michael.mygithubuserapp.database.FavoriteUser
import com.michael.mygithubuserapp.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application): ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun insert(favUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favUser)
    }
    fun delete(favUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favUser)
    }

    fun getAllFavUser(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavUsers()
    fun checkFav(username: String): LiveData<Boolean> = mFavoriteUserRepository.checkFav(username)
}