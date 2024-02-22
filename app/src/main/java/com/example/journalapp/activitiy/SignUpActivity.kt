package com.example.journalapp.activitiy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.journalapp.JournalUser
import com.example.journalapp.R
import com.example.journalapp.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        firebaseAuth=Firebase.auth

        binding.signuploginbtn.setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {
        val email=binding.signupemail.text.toString()
        val password=binding.signuppassword.text.toString()


        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAGY", "createUserWithEmail:success")

                    var journal:JournalUser=JournalUser.instance!!

                    journal.userId= firebaseAuth.currentUser?.uid.toString()
                    journal.userName=firebaseAuth.currentUser?.displayName.toString()

                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAGY", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    var journal:JournalUser=JournalUser.instance!!
                    journal.userId=null
                    journal.userName=null
                }
            }
    }

/*    private fun updateUI(user: FirebaseUser?) {
    }*/

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth.currentUser
        if (currentUser!=null){
            reload()
        }
    }

    public fun reload() {

    }
}