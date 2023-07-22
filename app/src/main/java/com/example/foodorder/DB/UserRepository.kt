package com.example.foodorder.DB

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDataDao) {
    val allUserData: LiveData<List<UserData>> = userDao.getAllUserData()


    suspend fun insert(userData: UserData) {
        userDao.insertUserData(userData)
    }
    suspend fun delete(userData: UserData) {
        userDao.deleteUserData(userData)
    }
suspend fun getUserByUsername(username: String): UserData? {
    return withContext(Dispatchers.IO) {
        userDao.getAllUser(username)
    }
}

    @WorkerThread
    suspend fun isValidUser(user: UserData): Flow<UserData?> {
        return userDao.getUserName(user.username, user.password)
    }
}