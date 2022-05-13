package com.fahima.uhaul.test.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fahima.uhaul.test.R
import com.fahima.uhaul.test.repository.UserRepository
import com.fahima.uhaul.test.viewmodel.UserViewModel
import com.fahima.uhaul.test.viewmodel.UserViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private val userRepository by lazy {UserRepository()}
    val viewModelProviderFactory by lazy { UserViewModelProviderFactory(application, userRepository) }
    val viewModel by lazy { ViewModelProvider(this, viewModelProviderFactory)[UserViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
