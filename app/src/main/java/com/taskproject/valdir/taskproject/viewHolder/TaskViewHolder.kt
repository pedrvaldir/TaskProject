package com.taskproject.valdir.taskproject.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.taskproject.valdir.taskproject.R
import com.taskproject.valdir.taskproject.entities.TaskEntity

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private val mTextDescription: TextView = itemView.findViewById(R.id.textDescription)

    fun bindData(task: TaskEntity){
        mTextDescription.text = task.description

    }
}