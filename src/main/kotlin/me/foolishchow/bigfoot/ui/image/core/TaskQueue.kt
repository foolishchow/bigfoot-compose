package me.foolishchow.bigfoot.ui.image.core

import me.foolishchow.bigfoot.ui.image.model.Task
import java.util.concurrent.LinkedBlockingQueue

class TaskQueue {
    var queue: LinkedBlockingQueue<Task> = LinkedBlockingQueue()

    companion object {
        val instance: TaskQueue by lazy { TaskQueue() }
    }

    fun add(task: Task): Boolean {
        return queue.add(task)
    }

    fun take(): Task {
        return queue.take()
    }

    fun isEmpty(): Boolean {
        return queue.isEmpty()
    }

    fun clear() {
        queue.clear()
    }
}