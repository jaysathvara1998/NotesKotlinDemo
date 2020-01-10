package com.example.noteskotlindemo.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.noteskotlindemo.Activities.MainActivity
import com.example.noteskotlindemo.FirebaseDatabaseHelper
import com.example.noteskotlindemo.Model.Task
import com.example.noteskotlindemo.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_task.*
import kotlinx.android.synthetic.main.fragment_add_task.view.*

/**
 * A simple [Fragment] subclass.
 */
class AddTaskFragment : Fragment() {
    var stTitle: String? = null
    var stNote:String? = null
    var id:String? = null
    val databaseHelper = FirebaseDatabaseHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_add_task, container, false)
        MainActivity.drawerLayout!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        view.ivBack.setOnClickListener {
            addTask()
            hideKeyboard()
            val fragment = NotesFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.content_frame, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return view
    }

    private fun addTask() {
        stTitle = etTitle.text.toString()
        stNote = etNote.text.toString()
        if (isNullOrBlank(stTitle) && isNullOrBlank(stNote)){
            Toast.makeText(context,"Empty note discard", LENGTH_LONG).show()
        }else{
            id=databaseHelper.databaseRef.push().key
            val task = Task(stTitle,stNote,id)
            databaseHelper.databaseRef.child(id!!).setValue(task)
            Toast.makeText(context,"Task Added",LENGTH_LONG).show()
        }
    }

    private fun isNullOrBlank(s: String?): Boolean {
        return s == null || s.trim { it <= ' ' } == ""
    }

    private fun hideKeyboard() {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.getApplicationWindowToken(), 0)
    }
}