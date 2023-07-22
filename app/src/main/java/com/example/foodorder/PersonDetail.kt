package com.example.foodorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.foodorder.DB.UserData
import com.example.foodorder.DB.UserDataViewModel
import com.example.foodorder.databinding.ActivityPersonDetailBinding

class PersonDetail : AppCompatActivity() {
    lateinit var binding: ActivityPersonDetailBinding
    lateinit var viewModel: UserDataViewModel
    lateinit var userName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.extras?.getString("username").toString()

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[UserDataViewModel::class.java]
        viewModel.searchUserByUsername(userName)
        viewModel.searchedUser.observe(this, Observer { user->
            if(user != null) {
                binding.userNameTxt.text = user.username
                binding.mobileTxt.text = user.mobile
                binding.emailTxt.text = user.email
                binding.passwordTxt.text = user.password
            }
        })

        binding.exit.setOnClickListener {
            finish()
        }
    }
}