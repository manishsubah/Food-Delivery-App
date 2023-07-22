package com.example.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.foodorder.DB.UserData
import com.example.foodorder.DB.UserDataViewModel
import com.example.foodorder.databinding.ActivityLoginPageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginPage : AppCompatActivity() {
    lateinit var binding: ActivityLoginPageBinding
    lateinit var viewModel: UserDataViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.emailuser.isCursorVisible = true
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[UserDataViewModel::class.java]

        binding.signup.setOnClickListener {
            val intent = Intent(this, RegistrationPage::class.java)
            startActivity(intent)
        }
        binding.signin.setOnClickListener {
            checkUserSignIn()
        }
    }

    private fun checkUserSignIn() {
        val usernameN = binding.emailuser.text.toString()
        val passwordN = binding.loginpassword.text.toString()
        if(usernameN.isNotEmpty() && passwordN.isNotEmpty()) {
            Toast.makeText(this, "Enter successfully", Toast.LENGTH_SHORT).show()
            viewModel.validateUser(usernameN, passwordN)
            viewModel.userLiveData.observe(this) {
                if(it != null) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("usernam",usernameN.toString())
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "User Not Registered.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}