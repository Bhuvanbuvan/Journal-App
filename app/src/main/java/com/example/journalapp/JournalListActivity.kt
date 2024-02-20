package com.example.journalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journalapp.adapter.JournalAdapter
import com.example.journalapp.databinding.ActivityJournalListBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class JournalListActivity : AppCompatActivity() {
    private lateinit var binding:ActivityJournalListBinding

    //Firebase Reference
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var user:FirebaseUser
    var db= FirebaseFirestore.getInstance()
    lateinit var storageRef:FirebaseStorage
    var collection:CollectionReference=db.collection("Journal")

    lateinit var jornalList:List<Journal>
    lateinit var jorAdapter: JournalAdapter
    lateinit var noPostTextView:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_journal_list)

        //Firebase Authentication
        firebaseAuth=Firebase.auth
        user= firebaseAuth.currentUser!!

        //Recyclerview
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager=LinearLayoutManager(this)

        //Post Array list
        jornalList= arrayListOf<Journal>()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_add->{
                if (user!=null  && firebaseAuth!=null) {
                    val intent=Intent(this@JournalListActivity,Journal_Activity::class.java)
                    startActivity(intent)
                }
            }
            R.id.action_signout->{
                if (user!=null && firebaseAuth!=null){
                    firebaseAuth.signOut()

                    var intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return onOptionsItemSelected(item)
    }
}