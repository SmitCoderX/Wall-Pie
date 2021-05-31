package com.smitcoderx.wall_pie.Repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.smitcoderx.wall_pie.Api.UnsplashApi
import com.smitcoderx.wall_pie.Db.UnsplashDatabase
import com.smitcoderx.wall_pie.Models.UnsplashPagingSource
import com.smitcoderx.wall_pie.Models.UnsplashPhoto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val db: UnsplashDatabase
) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(unsplashApi, query) }
        ).liveData

    suspend fun insert(photo: UnsplashPhoto) = db.unsplashDao().insert(photo)

    suspend fun delete(photo: UnsplashPhoto) = db.unsplashDao().delete(photo)

    fun getAllPhotos() = db.unsplashDao().getAllPhotos()

}