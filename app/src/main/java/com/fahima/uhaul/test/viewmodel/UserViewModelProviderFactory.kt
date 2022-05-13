package com.fahima.uhaul.test.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fahima.uhaul.test.repository.UserRepository

class UserViewModelProviderFactory(
    private val app: Application,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            UserViewModel::class.java.isAssignableFrom(modelClass) ->
                UserViewModel(app, userRepository) as T
            UserPostViewModel::class.java.isAssignableFrom(modelClass) ->
                UserPostViewModel(app, userRepository) as T
            else -> modelClass.newInstance()
        }
}