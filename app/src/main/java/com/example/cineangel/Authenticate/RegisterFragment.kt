package com.example.cineangel.Authenticate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cineangel.R
import com.example.cineangel.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        binding.registerBtn.setOnClickListener{
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val phone = binding.etPhone.text.toString()
            val data = User(username = username, email = email, password = password, phone = phone, role = "user")
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                Toast.makeText(activity, "Gagal membuat Akun.", Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{task ->
                        if(task.isSuccessful){
                            Log.d("Hallo", task.result.toString())
                            val user = auth.currentUser
                            val userId = user?.uid
                            if (userId != null){
                                firestore.collection("users").document(userId)
                                    .set(data)
                                    .addOnSuccessListener {
                                        //DATA PENGGUNA BERHASIL DISIMPAN
                                            val intentView = Intent(activity,LoginFragment::class.java)
                                            startActivity(intentView)
                                    }
                                    .addOnFailureListener{e->
                                        //GAGAL MENYIMPAN DATA PENGGUNA
                                        Log.w("Firestore", "Gagal menyimpan data pengguna",e)
                                    }
                            }
                        }else{
                            //GAGAL MEMBUAT AKUN PENGGUNA BARU
                            Toast.makeText(activity,"Gagal membuat akun.", Toast.LENGTH_SHORT).show()
                            Log.d("Error", task.toString())
                        }
                    }
                    .addOnFailureListener{e->
                        //TANGKAP DAN TAMPILKAN KESALAHAN
                        Toast.makeText(activity,"Gagal; mendaftar: ${e.message}", Toast.LENGTH_SHORT).show()

                        //Anda juga dapat menampilkan informasi kesalahan lebih detail ke logcat
                        Log.e("FirebaseAuth", "Gagal mendaftar", e)
                    }
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}