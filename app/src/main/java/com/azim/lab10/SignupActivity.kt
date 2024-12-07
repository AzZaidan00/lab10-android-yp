package com.azim.lab10

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.azim.lab10.databinding.ActivityMainBinding
import com.azim.lab10.databinding.ActivitySignupBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        // retrieve the connection to firebaseauth (connect to firebase auth)
        auth = FirebaseAuth.getInstance()

        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {
            val email = binding.regEmailEditText.text.toString()
            val password = binding.regPasswordEditText.text.toString()
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) {task ->
                    if (task.isSuccessful) {

                        // create new person
                        val newPerson = hashMapOf(
                            "name" to binding.regNameEditText.text.toString().trim(),
                            "city" to binding.regCityEditText.text.toString().trim(),
                            "country" to binding.regCountryEditText.text.toString().trim(),
                            "phone" to binding.regPhoneEditText.text.toString().trim(),
                            "email" to binding.regEmailEditText.text.toString().trim()
                            )

                        // Add a new document with a generate ID
                        db.collection("users")
                            .document(auth.currentUser!!.uid)
                            .set(newPerson)

                        Snackbar.make(binding.root,"Successfully Registered",
                            Snackbar.LENGTH_LONG).show()
                        finish()
                    } else {
                        println(task.exception)
                        Snackbar.make(binding.root,"Registration Failed!",
                            Snackbar.LENGTH_LONG).show()
                    }
                }
        }

    }
}