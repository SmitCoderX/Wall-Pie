package com.smitcoderx.wall_pie.Util

import androidx.room.TypeConverter
import com.smitcoderx.wall_pie.Models.UnsplashPhoto

class Convertor {

    @TypeConverter
    fun fromUrls(urls: UnsplashPhoto.UnsplashUrls): String {
        return urls.full
    }

    @TypeConverter
    fun toUrls(full: String): UnsplashPhoto.UnsplashUrls {
        return UnsplashPhoto.UnsplashUrls(full, full, full, full, full)
    }

    @TypeConverter
    fun fromUser(user: UnsplashPhoto.UnsplashUser): String {
        return user.name
    }

    @TypeConverter
    fun toUser(name: String): UnsplashPhoto.UnsplashUser {
        return UnsplashPhoto.UnsplashUser(name, name)
    }

}