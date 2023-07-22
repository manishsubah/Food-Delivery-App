package com.example.foodorder

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.foodorder.DBcart.CartData
import com.example.foodorder.DBcart.CartDataViewModel
import com.example.foodorder.databinding.ActivityShowDetailItemBinding
import java.io.File
import java.io.FileOutputStream

class ShowDetailItem : AppCompatActivity() {
    lateinit var binding: ActivityShowDetailItemBinding
    lateinit var viewModel: CartDataViewModel
    private lateinit var addString: String
    private lateinit var priceItem: String
    var numberOrder = 1
    lateinit var userName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDetailItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[CartDataViewModel::class.java]

        // Popular Items
        binding.titleTxt.text = intent.extras?.getString("title")
        addString = intent.extras?.getString("price").toString()
        priceItem = addString
        binding.priceTxt.text = String.format("₹ $addString")
        userName = intent.extras?.getString("username").toString()
        intent.extras?.let { binding.picFood.setImageResource(it.getInt("pic")) }

        binding.countOrderTxt.text = numberOrder.toString()

        binding.minus.setOnClickListener {
            if(numberOrder > 1) {
                numberOrder -= 1;
                binding.countOrderTxt.text = numberOrder.toString()
            }
        }

        binding.plus.setOnClickListener {
            numberOrder += 1;
            binding.countOrderTxt.text = numberOrder.toString()
        }
        binding.addToCartBtn.setOnClickListener {
            addCartToDatabase()
        }
    }

    private fun addCartToDatabase() {
        var imagePath: String = ""
        val imageDrawable = binding.picFood.drawable
        if (imageDrawable is BitmapDrawable) {
            val bitmap = imageDrawable.bitmap
            val file = File.createTempFile("image", ".jpg", applicationContext.cacheDir)
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.close()

            imagePath = file.absolutePath
            // Store the imagePath or use it as needed
        }

        val titleFood = binding.titleTxt.text.toString()
        val units = binding.countOrderTxt.text.toString()

        viewModel.insertCart(CartData(userName, titleFood, priceItem, imagePath, units))
        Toast.makeText(this, "Cart Data Stored.", Toast.LENGTH_SHORT).show()
        finish()
    }
}