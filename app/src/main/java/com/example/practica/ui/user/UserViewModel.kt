package com.example.practica.ui.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica.data.entity.User
import com.example.practica.repository.contract.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel @ViewModelInject constructor(private val repo: UserRepository) : ViewModel() {

    private val _user:  MutableLiveData<List<User>> by lazy { MutableLiveData<List<User>>() }
    val user = _user

    init {
        loadAll()
    }
    fun save(user: User){
        viewModelScope.launch(Dispatchers.Main){
            repo.save(user)
        }
    }

    fun loadAll() {
        viewModelScope.launch(Dispatchers.Main) {
             repo.loadAll().collect{
                 user.value = it
             }
        }
    }
}

