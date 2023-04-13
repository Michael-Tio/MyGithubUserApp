package com.michael.mygithubuserapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.michael.mygithubuserapp.database.FavoriteUser
import com.michael.mygithubuserapp.database.FavoriteUserDao
import com.michael.mygithubuserapp.database.FavoriteUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository (application: Application) {
    private val mfavUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mfavUserDao = db.favoriteUserDao()
    }
    fun getAllFavUsers(): LiveData<List<FavoriteUser>> = mfavUserDao.getFavoriteUser()
    fun checkFav(username:String): LiveData<Boolean> = mfavUserDao.checkFav(username)
    fun insert(fav: FavoriteUser) {
        executorService.execute { mfavUserDao.insert(fav) }
    }
    fun delete(fav: FavoriteUser) {
        executorService.execute { mfavUserDao.delete(fav) }
    }
    fun update(fav: FavoriteUser) {
        executorService.execute { mfavUserDao.update(fav) }
    }
}