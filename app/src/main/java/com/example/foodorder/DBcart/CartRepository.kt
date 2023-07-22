package com.example.foodorder.DBcart

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class CartRepository(private val cartDao: CartDataDao) {
    val allCartData: LiveData<List<CartData>> = cartDao.getAllCartData()

    suspend fun insert(cartData: CartData) {
        cartDao.insertCartData(cartData)
    }
    suspend fun delete(cartData: CartData) {
        cartDao.deleteCartData(cartData)
    }
    @WorkerThread
    suspend fun isValidataCart(cart: CartData) : Flow<CartData?> {
        return cartDao.getUserCartData(cart.username)
    }

    suspend fun getUserByUsername(username: String): List<CartData> {
        return withContext(Dispatchers.IO) {
            cartDao.getDataUsername(username)
        }
    }
}