package com.example.practica.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.practica.data.entity.User
import com.example.practica.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RosterViewModel @ViewModelInject constructor(
    private val repo: UserRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _listUsers:  MutableLiveData<List<User>> by lazy { MutableLiveData<List<User>>() }
    val listUser = _listUsers



    private val _user:  MutableLiveData<User> by lazy { MutableLiveData<User>() }
    val user = _listUsers

    init {
        loadAll()
    }

    fun save(user: User){
        viewModelScope.launch(Dispatchers.Main){
            repo.save(user)
        }
    }

    fun update(user: User){
        viewModelScope.launch(Dispatchers.Main){
            repo.update(user)
        }
    }

    fun delete(user: User){
        viewModelScope.launch(Dispatchers.Main){
            repo.delete(user)
        }
    }
    fun test(): Int?{
        return savedStateHandle.get<Int>(RosterFragment.USER_ID)
    }

    private fun loadAll() {
        viewModelScope.launch(Dispatchers.Main) {
             repo.loadAll().collect{
                 _listUsers.value = it
             }
        }
    }
    private fun getUser(id: Int){
        viewModelScope.launch(Dispatchers.Main){
            repo.getUser(id).collect{
                _user.value = it
            }
        }
    }
}

