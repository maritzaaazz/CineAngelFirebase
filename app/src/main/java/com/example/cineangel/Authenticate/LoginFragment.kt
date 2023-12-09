package com.example.cineangel.Authenticate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cineangel.Admin.HomePageAdminActivity
import com.example.cineangel.User.HomePageUserActivity
import com.example.cineangel.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentLoginBinding
    private var auth =  FirebaseAuth.getInstance()


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
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val auth = FirebaseAuth.getInstance()

        binding.loginBtn.setOnClickListener{
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(activity, "Email dan password harus diisi.", Toast.LENGTH_SHORT).show()
            }else{
                loginUser(email,password)
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login berhasil
                    val user = auth.currentUser
                    val userId = user?.uid

                    if (userId != null) {
                        // Mengecek dokumen di Firestore berdasarkan UID pengguna
                        val firestore = FirebaseFirestore.getInstance()
                        val userDocumentRef = firestore.collection("users").document(userId)

                        userDocumentRef.get()
                            .addOnSuccessListener { documentSnapshot ->
                                if (documentSnapshot.exists()) {
                                    // Dokumen ditemukan
                                    val role = documentSnapshot.getString("role")

                                    if (role == "user") {
                                        // Role sesuai, lanjutkan ke halaman beranda atau tindakan lain
                                        val intentView = Intent(activity,HomePageUserActivity::class.java)
                                        startActivity(intentView)
                                        Log.d("Login", "Role user, login berhasil")
                                        Toast.makeText(activity, "Login berhasil sebagai user", Toast.LENGTH_SHORT).show()
                                    } else {
                                        // Role tidak sesuai
                                        val intentView = Intent(activity,HomePageAdminActivity::class.java)
                                        startActivity(intentView)
                                        Log.e("Login", "Role admin, login berhasil")
                                        Toast.makeText(activity, "Login berhasil sebagai admin", Toast.LENGTH_SHORT).show()
                                        auth.signOut() // Logout pengguna karena role tidak sesuai
                                    }
                                } else {
                                    // Dokumen tidak ditemukan
                                    Log.e("Login", "Dokumen pengguna tidak ditemukan di Firestore")
                                    Toast.makeText(activity, "Login gagal, dokumen pengguna tidak ditemukan", Toast.LENGTH_SHORT).show()
                                    auth.signOut() // Logout pengguna karena dokumen tidak ditemukan
                                }
                            }
                            .addOnFailureListener { e ->
                                // Gagal mengambil dokumen
                                Log.e("Login", "Gagal mengambil dokumen pengguna: ${e.message}")
                                Toast.makeText(activity, "Login gagal, ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    // Login gagal
                    Log.e("Login", "Login gagal: ${task.exception?.message}")
                    Toast.makeText(activity, "Login gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}