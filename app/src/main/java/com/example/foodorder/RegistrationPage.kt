package com.example.foodorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.foodorder.DB.UserData
import com.example.foodorder.DB.UserDataViewModel
import com.example.foodorder.databinding.ActivityRegistrationPageBinding

class RegistrationPage : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationPageBinding
    lateinit var viewModel: UserDataViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[UserDataViewModel::class.java]



        binding.signintwo.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()

        }
        binding.signup.setOnClickListener {
            passwordCheck()
        }
    }

    private fun passwordCheck() {
        val password = binding.passwordone.text.toString()
        val conformPassword = binding.passwordtwo.text.toString()
        if(password == conformPassword) {
            submitData()
        } else {
            Toast.makeText(this, "Password not matched", Toast.LENGTH_LONG).show()
        }

    }

    private fun submitData() {
        val username = binding.username.text.toString()
        val mobile = binding.mobile.text.toString()
        val email = binding.emailadd.text.toString()
        val password = binding.passwordone.text.toString()
        if(username.isNotEmpty() && mobile.isNotEmpty() && email.isNotEmpty()) {
            viewModel.insertNote(UserData(username, mobile, email, password))
            Toast.makeText(this, "User Registered.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }
    }
}