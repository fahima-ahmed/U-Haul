package com.fahima.uhaul.test.api

import com.fahima.uhaul.test.model.Post
import com.fahima.uhaul.test.model.User
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserAPI {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("posts")
    suspend fun getUserPosts(@Query("userId") userId: Int): Response<List<Post>>

    @DELETE("posts/{postId}")
    suspend fun deletePost(@Path("postId") postId: Int)
}