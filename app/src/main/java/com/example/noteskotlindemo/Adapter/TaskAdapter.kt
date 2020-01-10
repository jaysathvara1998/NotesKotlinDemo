package com.example.noteskotlindemo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.noteskotlindemo.Model.Task
import com.example.noteskotlindemo.R
import java.util.ArrayList

class TaskAdapter( ) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    var taskList: ArrayList<Task>? = null
    var context:Context? = null
    var stTitle: String?=null
    var stNote:String? = null

    constructor(context: Context,taskList: ArrayList<Task>) : this() {
        this.context=context
        this.taskList=taskList
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var note: TextView

        init {
            title = view.findViewById(R.id.tvTitle)
            note = view.findViewById(R.id.tvNote)
        }
    }

    override fun getItemCount(): Int {
        return taskList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        stTitle = taskList!!.get(position).title
        stNote = taskList!!.get(position).note
        holder.title.text = stTitle
        holder.note.text = stNote
        if (isNullOrBlank(stTitle)) {
            holder.title.visibility = View.GONE
        } else if (isNullOrBlank(stNote)) {
            holder.note.visibility = View.GONE
        }

        holder.itemView.setOnClickListener{
            
        }
    }

    private fun isNullOrBlank(s: String?): Boolean {
        return s == null || s.trim { it <= ' ' } == ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_list, parent, false)
        return ViewHolder(itemView)
    }
}