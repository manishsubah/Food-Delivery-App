package com.example.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorder.DBcart.CartData
import com.example.foodorder.DBcart.CartDataAdapter
import com.example.foodorder.DBcart.CartDataViewModel
import com.example.foodorder.DBcart.ICartDataAdapter
import com.example.foodorder.databinding.ActivityCartListBinding

class CartList : AppCompatActivity(), ICartDataAdapter {
    lateinit var viewModel: CartDataViewModel
    lateinit var binding: ActivityCartListBinding
    lateinit var userName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userName = intent.extras?.getString("username").toString()

        binding = ActivityCartListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cartListRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CartDataAdapter(this, this)
        binding.cartListRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[CartDataViewModel::class.java]
        viewModel.searchUserByUsername(userName)

        var items: Int = 0
        var itemPrice: Int = 0
        var price: Int = 0
        var finalPrice: Int = 0
        var itemNumber: Int = 0
        viewModel.searchedUser.observe(this, Observer { users->
            if(users.isNotEmpty()) {
                for(user in users) {
                    items = user.units.toInt()
                    itemNumber += items
                    itemPrice = user.unitPrice.toInt()
                    price = items.toInt() * itemPrice.toInt()
                    finalPrice += price
                }
                binding.totalItemTxt.text = itemNumber.toString()
                binding.totalTxt.text = finalPrice.toString()
            }
        })

        viewModel.allCartData.observe(this, Observer { list->
            list?.let { adapter.updateList(it) }
        })

        binding.checkOut.setOnClickListener {
            val intent = Intent(this, OrderComplete::class.java)
            startActivity(intent)
        }
    }

    override fun onCartItemClicked(cartData: CartData) {
        viewModel.deleteCart(cartData)
        Toast.makeText(this, "${cartData.titleFood} deleted.", Toast.LENGTH_SHORT).show()
    }
}