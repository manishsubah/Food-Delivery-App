package com.example.foodorder.DB

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserData(usersData: UserData)

    @Delete
    fun deleteUserData(usersData: UserData)

    @Query("SELECT * FROM user_data")
    fun getAllUserData() : LiveData<List<UserData>>

    @Query("SELECT * FROM user_data WHERE username = :usernm")
    fun getAllUser(usernm: String): UserData?

    @Query("SELECT * FROM user_data ORDER BY username ASC")
    fun getAllUserName() : LiveData<List<UserData>>

    @Query("SELECT * FROM user_data WHERE username = :uname AND password = :passuser LIMIT 1")
    fun getUserName(uname: String, passuser: String) : Flow<UserData?>
}