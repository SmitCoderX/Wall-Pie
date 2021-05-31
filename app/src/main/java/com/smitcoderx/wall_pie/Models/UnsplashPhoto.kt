package com.smitcoderx.wall_pie.Models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "photoTable")

@Parcelize
data class UnsplashPhoto(
    @PrimaryKey(autoGenerate = true)
    val prim_id: Int? = null,

    val id: String?,
    val description: String?,
    val likes: Int?,
    val urls: UnsplashUrls?,
    val user: UnsplashUser?,
) : Parcelable {

    @Parcelize
    data class UnsplashUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : Parcelable


    @Parcelize
    data class UnsplashUser(
        val name: String,
        val username: String,
//        val profile_image: UnsplashUserProfile,
    ) : Parcelable
//        val attributionUrl get() = "https://unsplash.com/$username?utm?_source=Wall-Pie&utm_medium=referral"


    /*@Parcelize
    data class UnsplashUserProfile(
        val small: String,
        val medium: String,
        val large: String
    ) : Parcelable*/
}