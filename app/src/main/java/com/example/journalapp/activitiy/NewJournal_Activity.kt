package com.example.journalapp.activitiy

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.journalapp.Journal
import com.example.journalapp.JournalUser
import com.example.journalapp.R
import com.example.journalapp.databinding.ActivityJournalBinding
import com.example.journalapp.databinding.ActivityJournalListBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.awaitAll
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import java.util.Date

class NewJournal_Activity : AppCompatActivity() {
    lateinit var binding:ActivityJournalBinding

    //creadiction
    var currentUserId:String=""
    var currentUserName:String=""

    //firebase
    lateinit var auth:FirebaseAuth
    lateinit var user:FirebaseUser
    var db:FirebaseFirestore=FirebaseFirestore.getInstance()
    lateinit var storageReference:StorageReference

    var collectionReference:CollectionReference=db.collection("Journal")
    lateinit var  imageUri:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_journal)

        storageReference=FirebaseStorage.getInstance().getReference()
        auth=Firebase.auth

        binding.postProgressBar.visibility=View.INVISIBLE
        if (JournalUser.instance!=null){
//            currentUserId= JournalUser.instance!!.userId.toString()
//
//            currentUserName= JournalUser.instance!!.userName.toString()
            currentUserId= auth.currentUser!!.uid.toString()
            currentUserName= auth.currentUser!!.displayName.toString()

            binding.PostTitle.text=currentUserName
        }
        binding.postsavebutton.setOnClickListener {
            saveJournal()
        }
        binding.postCamButton.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,100)
        }

    }

    private fun saveJournal() {
        var title:String=binding.postTitleEt.text.toString().trim()
        var thoughts:String=binding.postDiscriptionEt.text.toString().trim()

        binding.postProgressBar.visibility=View.VISIBLE

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(thoughts) && imageUri!=null){
            //saving the path of images in storage
            // .../journal_image/our_image.png
            val filePath:StorageReference=storageReference.child("journal_images")
                .child("my_image_"+ com.google.firebase.Timestamp.now().seconds)

            //uploading the image
            filePath.putFile(imageUri).addOnSuccessListener {
                filePath.downloadUrl.addOnSuccessListener {
                    var imageUri:String=it.toString()
                    var timestamp:Timestamp=Timestamp(Date())
                    //creating the journal of the object
                    var journal:Journal= Journal(
                        title,
                        thoughts,
                        imageUri,
                        currentUserId,
                        timestamp,
                        currentUserName
                        )

                    collectionReference.add(journal)
                        .addOnSuccessListener {
                            binding.postProgressBar.visibility=View.INVISIBLE
                            var intent=Intent(this,JournalListActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                }
            }.removeOnFailureListener {
                binding.postProgressBar.visibility=View.INVISIBLE

            }

        }else{
            binding.postProgressBar.visibility=View.INVISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==100 && resultCode== RESULT_OK){
            imageUri= data?.data!!
            binding.postImageView.setImageURI(imageUri)
        }
    }

    override fun onStart() {
        super.onStart()
        user=auth.currentUser!!
    }

    override fun onStop() {
        super.onStop()
        if (auth!=null){

        }
    }
}