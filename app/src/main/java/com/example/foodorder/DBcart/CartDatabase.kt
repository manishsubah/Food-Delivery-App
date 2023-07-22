package com.example.foodorder.DBcart

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodorder.DB.UserData

@Database(entities = arrayOf(CartData::class), version = 1, exportSchema = false)
abstract class CartDatabase : RoomDatabase() {

    abstract fun getAllCartData(): CartDataDao
    companion object { // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: CartDatabase? = null
        fun getDatabase(context: Context) : CartDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, CartDatabase::class.java, "cart_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}