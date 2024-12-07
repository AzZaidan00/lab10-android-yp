package com.azim.lab10

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.azim.lab10.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    // to get the reference to Firestore
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        val userId = intent.getStringExtra("userId")!!


        // Customer is your database
        firestore.collection("users")
            .document(userId).get().addOnCompleteListener {
                task ->
                if (task.isSuccessful){
                    val document = task.result
                    binding.readNameTextView.text = document.get("name").toString()
                    binding.readEmailTextView.text = document.get("email").toString()
                }
            }
    }

}