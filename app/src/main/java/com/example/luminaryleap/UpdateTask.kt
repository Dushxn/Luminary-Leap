package com.example.luminaryleap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luminaryleap.Model.TaskModel
import com.example.luminaryleap.Util.DatabaseHandler

class UpdateTask : AppCompatActivity() {

    private lateinit var dbHandler: DatabaseHandler
    private lateinit var newUpdateText: EditText
    private var taskId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_task)

        dbHandler = DatabaseHandler(this)
        newUpdateText = findViewById(R.id.newUpdateText)

        // Get the task id and pre-fill the EditText if passed from MainActivity
        val intent = intent
        taskId = intent.getIntExtra("task_id", -1)
        if (taskId != -1) {
            val task = dbHandler.readTasks().find { it.taskId == taskId }
            if (task != null) {
                newUpdateText.setText(task.task)
            }
        }

        val updateTaskButton: Button = findViewById(R.id.UpdateTaskbtn)
        updateTaskButton.setOnClickListener {
            val updatedTask = newUpdateText.text.toString().trim()
            if (updatedTask.isNotEmpty() && taskId != -1) {
                if (dbHandler.updateTask(taskId, updatedTask)) {
                    Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show()
                    finish() // Close the UpdateTask activity
                } else {
                    Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Please enter a task and ensure a task is selected",
                    Toast.LENGTH_SHORT
                ).show()
            }}}}