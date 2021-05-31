package com.smitcoderx.wall_pie.Ui

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.smitcoderx.wall_pie.Models.UnsplashPhoto
import com.smitcoderx.wall_pie.Repository.UnsplashRepository
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

class UnsplashViewModel @ViewModelInject constructor(
    private val repository: UnsplashRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERTY)
    val photos = currentQuery.switchMap { query ->
        repository.getSearchResults(query).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    fun savePhoto(photo: UnsplashPhoto) = viewModelScope.launch {
        repository.insert(photo)
    }

    fun deletePhoto(photo: UnsplashPhoto) = viewModelScope.launch {
        repository.delete(photo)
    }

    fun savedPhotos() = repository.getAllPhotos()

    fun downloadPhotos(context: Context, url: String) {
        val request = DownloadManager.Request(
            Uri.parse(url)
        )
        request.apply {
            allowScanningByMediaScanner()
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES + "/Wall-Pie", "WallPie-${System.currentTimeMillis()}.jpg")
        }

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERTY = "popular"
    }

}