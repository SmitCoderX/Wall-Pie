package com.smitcoderx.wall_pie.Di

import android.app.Application
import androidx.room.Room
import com.smitcoderx.wall_pie.Db.UnsplashDatabase
import com.smitcoderx.wall_pie.Ui.WallPieApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app, UnsplashDatabase::class.java, "photo_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUnsplashDao(db: UnsplashDatabase) =
        db.unsplashDao()

}