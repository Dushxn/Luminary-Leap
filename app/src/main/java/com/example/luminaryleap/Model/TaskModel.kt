package com.example.luminaryleap.Model

class TaskModel {
    var taskId: Int = 0 // Unique identifier for the task
    var status: Int = 0
    var task: String = ""

    constructor(taskId: Int, status: Int, task: String) {
        this.taskId = taskId
        this.status = status
        this.task = task
    }

    constructor() {}
}