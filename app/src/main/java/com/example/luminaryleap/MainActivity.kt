package com.example.luminaryleap

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luminaryleap.Adaptor.ToDoAdapter
import com.example.luminaryleap.Model.TaskModel

class MainActivity : AppCompatActivity() {

    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskAdapter: ToDoAdapter
    private lateinit var taskList: MutableList<TaskModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        taskRecyclerView = findViewById(R.id.TaskList)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskList = mutableListOf() // Initialize taskList as an empty mutable list
        taskAdapter = ToDoAdapter(this, taskList)
        taskRecyclerView.adapter = taskAdapter

        val task = TaskModel().apply {
            task = "This is a task"
            status = 0
            id = 1
        }

        taskList.add(task)
        taskList.add(task)
        taskList.add(task)
        taskList.add(task)
    }
}
