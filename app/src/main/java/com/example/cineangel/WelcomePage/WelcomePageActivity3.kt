package com.example.cineangel.WelcomePage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cineangel.Authenticate.AuthenticationActivity
import com.example.cineangel.Authenticate.TabAdapter
import com.example.cineangel.R
import com.example.cineangel.databinding.ActivityWelcomePage2Binding
import com.example.cineangel.databinding.ActivityWelcomePage3Binding

class WelcomePageActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomePage3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomePage3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            btnGetStarted.setOnClickListener{
                val intentView = Intent(this@WelcomePageActivity3,AuthenticationActivity::class.java)
                startActivity(intentView)
            }
        }


    }
}