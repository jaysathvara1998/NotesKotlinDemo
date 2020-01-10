package com.example.noteskotlindemo

import com.example.noteskotlindemo.Model.Task
import com.google.firebase.database.FirebaseDatabase

class FirebaseDatabaseHelper {

    val databaseRef = FirebaseDatabase.getInstance().getReference("tasks")

    fun addTask(id:String,title:String,note:String){
        val id = databaseRef.push().key
        databaseRef.child(id!!).setValue(Task(title,note,id))
    }
}