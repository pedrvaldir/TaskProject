package com.taskproject.valdir.taskproject.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.taskproject.valdir.taskproject.R
import com.taskproject.valdir.taskproject.viewHolder.TaskViewHolder

class TaskListAdapter: RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        //inflar o layout item
        val context = parent?.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_task_list, parent, false)

        return TaskViewHolder(view)

    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(p0: TaskViewHolder, p1: Int) {

    }
}