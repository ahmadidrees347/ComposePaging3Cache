package com.compose.paging3.data.remote

import com.compose.paging3.model.SearchResult
import com.compose.paging3.model.UnsplashImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi {

//    @Headers("Authorization: Client-ID ${BuildConfig.API_KEY}")
    @Headers("Authorization: Client-ID ihdueCJa7ScTH_Q2OlI7MNl10uEevVb7igx1XADBz-w")
    @GET("/photos")
    suspend fun getAllImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<UnsplashImage>

    @Headers("Authorization: Client-ID ihdueCJa7ScTH_Q2OlI7MNl10uEevVb7igx1XADBz-w")
    @GET("/search/photos")
    suspend fun searchImages(
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): SearchResult

}