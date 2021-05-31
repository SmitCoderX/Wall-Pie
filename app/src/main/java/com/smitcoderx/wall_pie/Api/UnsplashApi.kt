package com.smitcoderx.wall_pie.Api

import com.smitcoderx.wall_pie.BuildConfig
import com.smitcoderx.wall_pie.Models.UnsplashResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val ACCESS_KEY = BuildConfig.UNSPLASH_ACCESS_KEY
    }

    @Headers("Accept-version: v1", "Authorization: Client-ID $ACCESS_KEY")
    @GET("search/photos")
    suspend fun SearchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse

}