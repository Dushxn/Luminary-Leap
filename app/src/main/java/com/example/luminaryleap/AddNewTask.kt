// AddNewTask.kt
package com.example.luminaryleap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luminaryleap.Model.TaskModel
import com.example.luminaryleap.R
import com.example.luminaryleap.Util.DatabaseHandler

class AddNewTask : AppCompatActivity() {
    private lateinit var dbHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_task)
        dbHandler = DatabaseHandler(this)

        val addTaskButton: Button = findViewById(R.id.AddTaskbtn)
        val newTaskText: EditText = findViewById(R.id.newTaskText)

        addTaskButton.setOnClickListener {
            val taskText = newTaskText.text.toString().trim()
            if (taskText.isNotEmpty()) {
                val task = TaskModel(0,0,taskText)
                if (dbHandler.insertTask(task)) {
                    Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in the task", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
