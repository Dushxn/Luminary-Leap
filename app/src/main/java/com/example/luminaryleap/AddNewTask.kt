package com.example.luminaryleap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.luminaryleap.Model.TaskModel
import com.example.luminaryleap.Util.DatabaseHandler

class AddNewTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_new_task)

        val context = this
        val addbtn : Button = findViewById(R.id.AddTaskbtn)
        val tasks : EditText = findViewById(R.id.newTaskText)

        addbtn.setOnClickListener {
            try {
                val taskText = tasks.text.toString()
                if (taskText.isNotEmpty()) {
                    val task = TaskModel(0,0, taskText)
                    val db = DatabaseHandler(context)
                    db.insertTask(task)
                    Toast.makeText(context, "Task added successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please fill in the task", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Failed to add task", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
