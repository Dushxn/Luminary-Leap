package com.example.luminaryleap.Util

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.luminaryleap.Model.TaskModel


val DATABASE_NAME = "TaskDB"
var TABLE_NAME = "Tasks"
var COL_ID = "id"
var COL_STATUS = "status"
var COL_TASKS = "tasks"

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_STATUS INTEGER, " +
                "$COL_TASKS VARCHAR(256));"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database schema upgrades if needed
    }

    fun insertTask(task: TaskModel): Boolean {
        val db = writableDatabase ?: return false

        val values = ContentValues().apply {
            put(COL_STATUS, task.status)
            put(COL_TASKS, task.task)
        }

        val result = db.insert(TABLE_NAME, null, values)
        db.close()

        return result != -1L
    }

    fun readTasks(): MutableList<TaskModel> {
        val tasks = mutableListOf<TaskModel>()

        val db = readableDatabase ?: return tasks

        val cursor: Cursor? = db.query(TABLE_NAME, null, null, null, null, null, null)

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndex(COL_ID))
                    val status = it.getInt(it.getColumnIndex(COL_STATUS))
                    val task = it.getString(it.getColumnIndex(COL_TASKS))

                    val taskModel = TaskModel(id,status, task)
                    tasks.add(taskModel)
                } while (it.moveToNext())
            }
        }

        db.close()
        return tasks
    }

    fun deleteTask(taskId: Int): Boolean {
        val db = writableDatabase ?: return false
        val result = db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(taskId.toString()))
        db.close()
        return result > 0
    }


    fun updateTask(taskId: Int, newTask: String): Boolean {
        val db = writableDatabase ?: return false
        val values = ContentValues().apply {
            put(COL_TASKS, newTask)
        }
        val result = db.update(TABLE_NAME, values, "$COL_ID = ?", arrayOf(taskId.toString()))
        db.close()
        return result > 0
    }



}