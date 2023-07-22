package com.example.foodorder.DBcart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodorder.DB.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CartDataViewModel(application: Application) : AndroidViewModel(application) {
    val repository: CartRepository
    val allCartData: LiveData<List<CartData>>
    val cartLiveData = MutableLiveData<CartData>()

    private val _searchedUser = MutableLiveData<List<CartData>>()
    val searchedUser: LiveData<List<CartData>> get() = _searchedUser

    init {
        val dao = CartDatabase.getDatabase(application).getAllCartData()
        repository = CartRepository(dao)
        allCartData = repository.allCartData
    }
    fun insertCart(cartData: CartData) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(cartData)
    }
    fun deleteCart(cartData: CartData) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(cartData)
    }
    fun validateCart(username: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.isValidataCart(CartData(username = username, titleFood = "", unitPrice = "",
        imageFood = "", units = "")).collect { cartLiveData.postValue(it) }
    }
    fun searchUserByUsername(username: String) {
        viewModelScope.launch {
            val user = repository.getUserByUsername(username)
            withContext(Dispatchers.Main){
                _searchedUser.value = user
            }
        }
    }


}