package com.example.journalapp

import java.sql.Timestamp

data class Journal(
     val title:String,
     val thoughts:String,
     val imageUrl:Int,

     val userId:String,
     val timeAdded:Timestamp,
     val username:String
    )
