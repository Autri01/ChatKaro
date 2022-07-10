package com.example.chatkaro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;
    private lateinit var database: DatabaseReference
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        // Initialize Firebase Auth
        auth = Firebase.auth

        btn_signup.setOnClickListener {
            val name=edt_name.text.toString()
            val email=edt_email_sign.text.toString()
            val password=edt_password_sign.text.toString()

            signUp(name,email,password)
        }
    }

    private fun signUp(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUsertoDatabase(name,email, auth.currentUser?.uid !! )
                    val intent= Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUsertoDatabase(name: String, email: String, uid: String) {
        database = Firebase.database.reference
        database.child("users").child(uid).setValue(User(name,email, uid))
    }
}