package com.example.cineangel.Authenticate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cineangel.R
import com.example.cineangel.databinding.ActivityAuthenticationBinding
import com.google.android.material.tabs.TabLayoutMediator

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            viewPager.adapter = TabAdapter(this@AuthenticationActivity)
            TabLayoutMediator(tabLayout, viewPager) {
                    tab, position ->
                tab.text = when(position) {
                    0 -> "Register"
                    1 -> "Login"
                    else -> ""
                }
            }.attach()
        }
    }
}