package com.fahima.uhaul.test.repository

import com.fahima.uhaul.test.api.RetrofitInstance
import com.fahima.uhaul.test.model.Post
import com.fahima.uhaul.test.model.User
import retrofit2.Response

class UserRepository {
    suspend fun getUsers(): Response<List<User>> {
        return RetrofitInstance.api.getUsers()
    }

    suspend fun getUserPosts(userid: Int): Response<List<Post>> {
        return RetrofitInstance.api.getUserPosts(userid)
    }

    suspend fun deletePost(postId: Int){
        return RetrofitInstance.api.deletePost(postId)
    }

}