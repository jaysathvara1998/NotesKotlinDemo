package com.example.noteskotlindemo.Fragment

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteskotlindemo.Adapter.TaskAdapter
import com.example.noteskotlindemo.Model.Task
import com.example.noteskotlindemo.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.fragment_notes.view.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class NotesFragment : Fragment() {
    private var tasks = ArrayList<Task>()
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_notes, container, false)

        init(view)

        clickListener(view)

        getData(view)

        return view
    }

    private fun clickListener(view: View?) {
        view!!.fab.setOnClickListener{
            val fragment = AddTaskFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.content_frame, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        view.switchLay.setOnClickListener {
            getData(view)
        }
    }

    private fun init(view: View?) {
        view!!.recyclerView.setHasFixedSize(true)
        if (view.switchLay.drawable.constantState == resources.getDrawable(R.drawable.horizontal_24).constantState){
            view.recyclerView.layoutManager = LinearLayoutManager(activity)
        }else{
//            view.recyclerView.layoutManager = GridLayoutManager(activity,2)
            view.recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        }
    }

    private fun getData(view: View) {
        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("tasks")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tasks.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val value = postSnapshot.getValue(Task::class.java)
                    if (value != null) {
                        tasks.add(value)
                    }
                }
                if(!tasks.isEmpty()){
                        if (view.switchLay.drawable.constantState == resources.getDrawable(R.drawable.horizontal_24).constantState){
                            view.recyclerView.layoutManager = LinearLayoutManager(activity)
                            view.switchLay.setImageResource(R.drawable.vertical_24)
                            view.recyclerView.visibility = View.VISIBLE
                            view.emptyListLayout.visibility = View.GONE
                            val adapter = activity?.let { TaskAdapter(it, tasks) }
                            view.recyclerView.adapter = adapter
                        }else{
//                            view.recyclerView.layoutManager = GridLayoutManager(activity,2)
                            view.recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                            view.switchLay.setImageResource(R.drawable.horizontal_24)
                            view.recyclerView.visibility = View.VISIBLE
                            view.emptyListLayout.visibility = View.GONE
                            val adapter = activity?.let { TaskAdapter(it, tasks) }
                            view.recyclerView.adapter = adapter
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}