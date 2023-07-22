package com.example.foodorder.DBcart

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart_user")
class CartData (val username: String, val titleFood: String,
                val unitPrice: String,
                val imageFood: String,
                val units: String) {
    @PrimaryKey(autoGenerate = true) var id = 0
}