package com.example.luminaryleap.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.luminaryleap.Model.TaskModel
import com.example.luminaryleap.R

class ToDoAdapter(
    private val taskList: MutableList<TaskModel>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onDeleteClick(position: Int, taskId: Int)
        fun onUpdateClick(position: Int, taskId: Int, newTask: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.tasklayout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = taskList[position]
        holder.task.text = item.task

        // Delete Button Click Listener
        holder.deleteButton.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                val taskId = taskList[adapterPosition].taskId
                listener.onDeleteClick(position, taskId)
            }
        }

        // Update Button Click Listener
        holder.updateButton.setOnClickListener {
            val newTask = holder.task.text.toString()
            val taskId = taskList[position].taskId
            listener.onUpdateClick(position, taskId, newTask)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var task: CheckBox = itemView.findViewById(R.id.TaskCheckBox)
        var deleteButton: ImageView = itemView.findViewById(R.id.delete)
        var updateButton: ImageView = itemView.findViewById(R.id.edit)
    }
}
