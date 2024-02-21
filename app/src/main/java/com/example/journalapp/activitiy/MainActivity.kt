package com.example.journalapp.activitiy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.journalapp.JournalUser
import com.example.journalapp.R
import com.example.journalapp.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_main)
        //Auth Ref
        auth=Firebase.auth
        binding.createbtn.setOnClickListener {
            val intent=Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.loginbtn.setOnClickListener {
            LoginWithEmailAndPassword(
                binding.email.text.toString().trim(),
                binding.password.text.toString().trim()
            )
        }



    }

    private fun LoginWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) {task->
            if (task.isSuccessful){

                var journal:JournalUser=JournalUser.instance!!
                journal.userId= auth.currentUser?.uid
                journal.userName= auth.currentUser?.displayName
                goToJournalList()
            }else{
                Toast.makeText(this,"Login Falier",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser=auth.currentUser
        if (currentUser!=null){
            goToJournalList()
        }
    }

    private fun goToJournalList() {
        val intent=Intent(this,JournalListActivity::class.java)
        startActivity(intent)
    }
}