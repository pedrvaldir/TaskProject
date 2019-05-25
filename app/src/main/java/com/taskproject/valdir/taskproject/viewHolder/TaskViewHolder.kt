package com.taskproject.valdir.taskproject.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.taskproject.valdir.taskproject.R
import com.taskproject.valdir.taskproject.entities.OnTaskListFragmentInteractionListener
import com.taskproject.valdir.taskproject.entities.TaskEntity
import com.taskproject.valdir.taskproject.repository.PriorityCacheConstants

class TaskViewHolder(itemView: View, val listener: OnTaskListFragmentInteractionListener)
    : RecyclerView.ViewHolder(itemView){

    private val mTextDescription: TextView = itemView.findViewById(R.id.textDescription)
    private val mTextPriority: TextView = itemView.findViewById(R.id.textPriority)
    private val mTextDate: TextView = itemView.findViewById(R.id.textDueDate)
    private val mImageTask: ImageView = itemView.findViewById(R.id.imageTask)

    fun bindData(task: TaskEntity){
        mTextDescription.text = task.description
        mTextPriority.text = PriorityCacheConstants.getPriorityDescription(task.priorityId)
        mTextDate.text = task.dueData

        if (task.complete){
            mImageTask.setImageResource(R.drawable.ic_done)
        }

        //Evento de clique para edição
        mTextDescription.setOnClickListener(View.OnClickListener {
            listener.onListClick(task.id)
        })
    }
}