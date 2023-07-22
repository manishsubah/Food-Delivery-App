package com.example.foodorder.DBcart

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface CartDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCartData(cartData: CartData)

    @Delete
    fun deleteCartData(cartData: CartData)

    @Query("SELECT * FROM cart_user ORDER BY id ASC")
    fun getAllCartData() : LiveData<List<CartData>>

    @Query("SELECT * FROM cart_user WHERE username = :uname")
    fun getDataUsername(uname: String): List<CartData>

    @Query("SELECT * FROM cart_user WHERE username = :usercart LIMIT 1")
    fun getUserCartData(usercart: String) : Flow<CartData?>




}