package com.example.luminaryleap.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.luminaryleap.MainActivity
import com.example.luminaryleap.Model.TaskModel
import com.example.luminaryleap.R

class ToDoAdapter(private val activity: MainActivity, private val todoList: MutableList<TaskModel>) :
    RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.tasklayout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = todoList[position]
        holder.task.text = item.task
        holder.task.isChecked = toBoolean(item.status)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    private fun toBoolean(n: Int): Boolean {
        return n != 0
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        var task: CheckBox

        init {
            task = view.findViewById(R.id.TaskCheckBox)
        }
    }
}
