package com.geekbrains.shoplist.ui.splash

import androidx.lifecycle.ViewModel
import com.geekbrains.shoplist.data.model.Repository

class SplashViewModel(private val repository: Repository = Repository) : ViewModel() {

    fun requestUser(email: String) {
        repository.emailUser = email
    }
}
