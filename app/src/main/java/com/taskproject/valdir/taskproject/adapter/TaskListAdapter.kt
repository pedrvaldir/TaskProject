package com.taskproject.valdir.taskproject.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.taskproject.valdir.taskproject.R
import com.taskproject.valdir.taskproject.entities.OnTaskListFragmentInteractionListener
import com.taskproject.valdir.taskproject.entities.TaskEntity
import com.taskproject.valdir.taskproject.viewHolder.TaskViewHolder

class TaskListAdapter(val taskList: List<TaskEntity>, val listener: OnTaskListFragmentInteractionListener) :
    RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        //inflar o layout item
        val context = parent?.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_task_list, parent, false)

        return TaskViewHolder(view, context,  listener)

    }

    override fun getItemCount(): Int {
        return taskList.count()//valor da recyclerview
    }

    //toda vez q uma linha é criada
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bindData(task)

    }
}