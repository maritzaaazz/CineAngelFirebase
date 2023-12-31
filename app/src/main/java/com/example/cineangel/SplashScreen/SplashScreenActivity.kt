package com.example.cineangel.SplashScreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.cineangel.R
import com.example.cineangel.WelcomePage.WelcomePageActivity1

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this@SplashScreenActivity, WelcomePageActivity1::class.java)
            startActivity(mainIntent)
            finish()
        }, splashTimeOut)
    }
}