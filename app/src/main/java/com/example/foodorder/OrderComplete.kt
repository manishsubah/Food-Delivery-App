package com.example.foodorder

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.foodorder.databinding.ActivityOrderCompleteBinding

class OrderComplete : AppCompatActivity() {
    lateinit var binding: ActivityOrderCompleteBinding
    private var channelId = "CHANNEL_ID"
    private var channelName = "SWAD RASOI"
    private var notificationId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        deliveryNotification()
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Swad Rasoi delivery notification.")
            .setContentText("Your Ordered food items are ready for delivery. Delivery will be reached within 30 minutes.")
            .setSmallIcon(R.drawable.swadrasoilogo)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Your Ordered food items are ready for delivery. Delivery will be reached within 30 minutes."))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        val notificationManager = NotificationManagerCompat.from(this)

        binding.submitOrder.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Notification permission missing.", Toast.LENGTH_SHORT).show()
            }
            notificationManager.notify(notificationId, notification)
        }
        binding.saveDeliveryAdd.setOnClickListener {
            Toast.makeText(this, "Delivery Address saved.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deliveryNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = ""
                lightColor = Color.GREEN
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}