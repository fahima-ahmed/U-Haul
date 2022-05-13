package com.fahima.uhaul.test.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fahima.uhaul.test.UserApplication
import com.fahima.uhaul.test.model.Post
import com.fahima.uhaul.test.repository.UserRepository
import com.fahima.uhaul.test.util.Resource
import com.fahima.uhaul.test.util.hasInternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException

class UserPostViewModel(
    app: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(app) {

    val userPosts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()
    private var isSortByAscending = true

    fun getUserPosts(userId: Int) = viewModelScope.launch(Dispatchers.IO) {
        safeUserPostCall(userId)
    }

    private suspend fun safeUserPostCall(userId: Int) {
        userPosts.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(getApplication<UserApplication>())) {
                val response = userRepository.getUserPosts(userId)
                userPosts.postValue(Resource.Success(response.body()!!))
            } else {
                userPosts.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> userPosts.postValue(Resource.Error("Network Failure"))
                else -> userPosts.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    fun deletePost(post: Post) {
        try {
            if (hasInternetConnection(getApplication<UserApplication>())) {
                userPosts.value = Resource.Success(userPosts.value!!.data!!.toMutableList().apply {
                    remove(post)
                })
                viewModelScope.launch(Dispatchers.IO) {
                    userRepository.deletePost(post.id!!)
                }
            } else {
                Log.e("TAG", "No internet connection")
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> Log.e("TAG", "Network Failure")
                else -> Log.e("TAG", "Conversion Error")
            }
        }
    }

    fun sortPost() {
        if (isSortByAscending) {
            userPosts.postValue(Resource.Success(userPosts.value!!.data!!.sortedBy { it.title }))
        } else {
            userPosts.postValue(Resource.Success(userPosts.value!!.data!!.sortedBy { it.title }
                .reversed()))
        }
        isSortByAscending = !isSortByAscending
    }


}












