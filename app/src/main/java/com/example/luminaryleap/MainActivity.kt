package com.example.luminaryleap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luminaryleap.Adaptor.ToDoAdapter
import com.example.luminaryleap.Model.TaskModel
import com.example.luminaryleap.Util.DatabaseHandler

class MainActivity : AppCompatActivity() {

    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskAdapter: ToDoAdapter
    private lateinit var taskList: MutableList<TaskModel>
    private lateinit var dbHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        dbHandler = DatabaseHandler(this)

        taskRecyclerView = findViewById(R.id.TaskList)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskList = dbHandler.readTasks() // Read tasks from the database
        taskAdapter = ToDoAdapter(taskList, object : ToDoAdapter.OnItemClickListener {
            override fun onDeleteClick(position: Int, taskId: Int) {
                dbHandler.deleteTask(taskId)
                taskList.removeAt(position)
                taskAdapter.notifyItemRemoved(position)
            }

            override fun onUpdateClick(position: Int, taskId: Int, newTask: String) {
                val taskModel = taskList[position]
                taskModel.task = newTask
                dbHandler.updateTask(taskId, newTask)
                taskAdapter.notifyItemChanged(position)
            }
        })
        taskRecyclerView.adapter = taskAdapter

        val addTaskButton: Button = findViewById(R.id.AddTask)
        addTaskButton.setOnClickListener {
            val intent = Intent(this, AddNewTask::class.java)
            startActivity(intent)
        }
    }
}
