package com.smitcoderx.wall_pie.Db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smitcoderx.wall_pie.Models.UnsplashPhoto
import com.smitcoderx.wall_pie.Util.Convertor

@Database(
    entities = [UnsplashPhoto::class],
    version = 1
)
@TypeConverters(Convertor::class)
abstract class UnsplashDatabase : RoomDatabase() {

    abstract fun unsplashDao(): UnsplashDao

}