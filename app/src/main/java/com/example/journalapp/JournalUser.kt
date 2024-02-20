package com.example.journalapp

import android.app.Application

class JournalUser:Application() {
    var userName:String?=null
    var userId:String?=null
    companion object{
        var instance:JournalUser?=null
            get() {
                if (field==null){
                    //create new Instance from the Journal User
                    field= JournalUser()
                }
                return field
            }

    }
}