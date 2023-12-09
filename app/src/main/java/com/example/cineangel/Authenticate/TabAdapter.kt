package com.example.cineangel.Authenticate

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(act: AppCompatActivity) : FragmentStateAdapter(act){
    // Override metode getItemCount() untuk menentukan jumlah total fragmen dalam adapter.
    override fun getItemCount(): Int {
        return 2 // Mengembalikan nilai 2 karena ada 2 fragmen.
    }

    // Override metode createFragment(position) untuk membuat fragmen berdasarkan posisi.
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RegisterFragment() // Jika posisi adalah 0, buat dan kembalikan fragmen RegisterFragment.
            1 -> LoginFragment()   // Jika posisi adalah 1, buat dan kembalikan fragmen LoginFragment.
            else -> throw IllegalArgumentException("Position out of array")
            // Jika posisi tidak valid (diluar 0 dan 1), lempar pengecualian IllegalArgumentException.
        }
    }
}