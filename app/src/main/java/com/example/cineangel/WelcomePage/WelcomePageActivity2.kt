package com.example.cineangel.WelcomePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.cineangel.R
import com.example.cineangel.databinding.ActivityWelcomePage1Binding
import com.example.cineangel.databinding.ActivityWelcomePage2Binding

class WelcomePageActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomePage2Binding
    private val splashTimeOut: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomePage2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this@WelcomePageActivity2, WelcomePageActivity3::class.java)
            startActivity(mainIntent)
            finish()
        }, splashTimeOut)
    }
}