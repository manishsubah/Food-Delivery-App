package com.example.foodorder.DB

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDataViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    val allUserData: LiveData<List<UserData>>
    val userLiveData = MutableLiveData<UserData>()

    private val _searchedUser = MutableLiveData<UserData?>()
    val searchedUser: LiveData<UserData?> get() = _searchedUser

    init {
            val dao = UserDatabase.getDatabase(application).getAllUserData()
            repository = UserRepository(dao)
            allUserData = repository.allUserData
    }
    fun insertNote(userData: UserData) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(userData)
    }
    fun validateUser(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.isValidUser(UserData(username = username, mobile = "", email = "", password = password)
        ).collect { userLiveData.postValue(it) }
    }

    fun searchUserByUsername(username: String) {
        viewModelScope.launch {
            val user = repository.getUserByUsername(username)
            withContext(Dispatchers.Main) {
                _searchedUser.value = user
            }
        }
    }


}