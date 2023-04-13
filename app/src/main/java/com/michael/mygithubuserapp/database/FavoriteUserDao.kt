package com.michael.mygithubuserapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(fav: FavoriteUser)

    @Update
    fun update(fav: FavoriteUser)

    @Delete
    fun delete(fav: FavoriteUser)

    @Query("SELECT * FROM favUser")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("SELECT EXISTS(SELECT * FROM favUser WHERE username = :username)")
    fun checkFav(username: String): LiveData<Boolean>
}