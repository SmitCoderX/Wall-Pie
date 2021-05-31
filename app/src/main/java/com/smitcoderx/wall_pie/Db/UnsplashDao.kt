package com.smitcoderx.wall_pie.Db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.smitcoderx.wall_pie.Models.UnsplashPhoto

@Dao
interface UnsplashDao {

    @Query("SELECT * FROM photoTable")
    fun getAllPhotos(): LiveData<List<UnsplashPhoto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: UnsplashPhoto)

    @Update
    suspend fun update(photo: UnsplashPhoto)

    @Delete
    suspend fun delete(photo: UnsplashPhoto)
}