package com.example.luminaryleap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

    private val REQUEST_CODE_ADD_TASK = 1 // Unique request code for handling result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHandler = DatabaseHandler(this)

        // Retrieve tasks from database
        taskList = dbHandler.readTasks()

        taskRecyclerView = findViewById(R.id.TaskList)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = ToDoAdapter(taskList, object : ToDoAdapter.OnItemClickListener {
            override fun onDeleteClick(position: Int, taskId: Int) {
                dbHandler.deleteTask(taskId)
                taskList.removeAt(position)
                taskAdapter.notifyItemRemoved(position)
            }

            override fun onUpdateClick(position: Int, taskId: Int, newTask: String) {
                val intent = Intent(this@MainActivity, UpdateTask::class.java) // Use 'this@MainActivity' for explicit reference
                intent.putExtra("task_id", taskId)
                startActivityForResult(intent, REQUEST_CODE_ADD_TASK) // Use startActivityForResult with request code
            }
        })
        taskRecyclerView.adapter = taskAdapter

        val addTaskButton: Button = findViewById(R.id.AddTask)
        addTaskButton.setOnClickListener {
            val intent = Intent(this, AddNewTask::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_TASK && resultCode == RESULT_OK) {
            val newTask = data?.getStringExtra("newTask") // Get new task from intent
            if (newTask != null) {
                // Add new task to database and list
                val task = TaskModel(0, 0, newTask)
                dbHandler.insertTask(task)
                taskList.add(task) // Add to in-memory list
                taskAdapter.notifyItemInserted(taskList.size - 1) // Notify adapter about new item
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHandler.close() // Close database connection
    }
}
