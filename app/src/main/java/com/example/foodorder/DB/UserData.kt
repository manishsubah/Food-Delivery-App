package com.example.foodorder.DB

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_data")
class UserData(val username: String,
                val mobile: String,
                val email: String,
                val password: String) {
    @PrimaryKey(autoGenerate = true) var id = 0
}