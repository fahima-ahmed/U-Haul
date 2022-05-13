package com.fahima.uhaul.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fahima.uhaul.test.UserApplication
import com.fahima.uhaul.test.model.User
import com.fahima.uhaul.test.repository.UserRepository
import com.fahima.uhaul.test.util.Resource
import com.fahima.uhaul.test.util.hasInternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException

class UserViewModel(
    app: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(app) {

    val users: MutableLiveData<Resource<List<User>>> = MutableLiveData()

    init {
        getUsers()
    }

    private fun getUsers() = viewModelScope.launch(Dispatchers.IO) {
        safeUsersCall()
    }

    private suspend fun safeUsersCall() {
        users.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(getApplication<UserApplication>())) {
                val response = userRepository.getUsers()
                users.postValue(Resource.Success(response.body()!!))
            } else {
                users.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> users.postValue(Resource.Error("Network Failure"))
                else -> users.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

}












