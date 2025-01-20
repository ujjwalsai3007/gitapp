package com.example.gitapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.gitapp.databinding.ActivitySearchUsernameBinding

class SearchUsernameActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchUsernameBinding
    private val sharedPreferences by lazy {
        getSharedPreferences("GitAppPrefs", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchUsernameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create Notification Channel (for Android 8.0+)
        createNotificationChannel()

        binding.searchButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            if (username.isEmpty()) {
                Toast.makeText(this, "Please enter a GitHub username", Toast.LENGTH_SHORT).show()
            } else {
                // Save username in SharedPreferences
                sharedPreferences.edit().putString("LAST_USERNAME", username).apply()

                // Show Welcome Notification
                showWelcomeNotification()

                // Navigate to HomeActivity
                val intent = Intent(this, HomeActivity::class.java).apply {
                    putExtra("USERNAME", username)
                }
                startActivity(intent)
            }
        }
    }

    private fun showWelcomeNotification() {
        val notificationId = 1
        val builder = NotificationCompat.Builder(this, "welcome_channel")
            .setSmallIcon(R.drawable.img_5) // Add an appropriate icon
            .setContentTitle("Welcome to GitHub Repository App!")
            .setContentText("Explore your repositories and stay updated with GitHub notifications.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Welcome Notifications"
            val descriptionText = "Channel for Welcome Notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("welcome_channel", name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}