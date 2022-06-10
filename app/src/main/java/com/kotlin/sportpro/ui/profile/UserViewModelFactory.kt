package com.kotlin.sportpro.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlin.sportpro.data.remote.user.UserRepository
import com.kotlin.sportpro.ui.home.SportViewModel

class UserViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) ->
                UserViewModel(repository) as T
            else -> throw IllegalArgumentException("Not found")
        }
    }
}
