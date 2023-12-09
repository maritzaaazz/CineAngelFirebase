package com.example.cineangel.WelcomePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.cineangel.R
import com.example.cineangel.databinding.ActivityWelcomePage1Binding

class WelcomePageActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomePage1Binding
    private val splashTimeOut: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomePage1Binding.inflate(layoutInflater)
        setContentView(binding.root)


        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this@WelcomePageActivity1, WelcomePageActivity2::class.java)
            startActivity(mainIntent)
            finish()
        }, splashTimeOut)

    }
}