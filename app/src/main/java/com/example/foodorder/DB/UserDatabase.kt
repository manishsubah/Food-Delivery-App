package com.example.foodorder.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(UserData::class), version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getAllUserData() : UserDataDao
    companion object { // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java, "user_database"
                    ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }

}