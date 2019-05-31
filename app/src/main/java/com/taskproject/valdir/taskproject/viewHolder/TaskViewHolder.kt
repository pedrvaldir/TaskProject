package com.taskproject.valdir.taskproject.viewHolder

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.taskproject.valdir.taskproject.R
import com.taskproject.valdir.taskproject.entities.OnTaskListFragmentInteractionListener
import com.taskproject.valdir.taskproject.entities.TaskEntity
import com.taskproject.valdir.taskproject.repository.PriorityCacheConstants

class TaskViewHolder(itemView: View, val context: Context, val listener: OnTaskListFragmentInteractionListener)
    : RecyclerView.ViewHolder(itemView){

    private val mTextDescription: TextView = itemView.findViewById(R.id.textDescription)
    private val mTextPriority: TextView = itemView.findViewById(R.id.textPriority)
    private val mTextDate: TextView = itemView.findViewById(R.id.textDueDate)
    private val mImageTask: ImageView = itemView.findViewById(R.id.imageTask)
    private val mRelativeLayout: RelativeLayout = itemView.findViewById(R.id.rvRowTask)

    fun bindData(task: TaskEntity){
        mTextDescription.text = task.description
        mTextPriority.text = PriorityCacheConstants.getPriorityDescription(task.priorityId)
        mTextDate.text = task.dueData

        if (task.complete){
            mImageTask.setImageResource(R.drawable.ic_done)
        }

        mRelativeLayout.setOnClickListener {
            listener.onListClick(task.id)
        }

        mRelativeLayout.setOnLongClickListener{
            showConfirmationDialog(task)
            true
        }
    }

    private fun showConfirmationDialog(task: TaskEntity){

        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.msg_title_dialog_task))
            .setMessage(context.getString(R.string.perg_remov_task) +
                    "\n ${task.description}")
            .setIcon(R.drawable.ic_delete)
            //.setPositiveButton("Remover") { _: DialogInterface, i->  listener.onDeleteClick(task.id)} <- Utilizando lambda, simplificado
            .setPositiveButton(context.getString(R.string.remover_dialog),
                handleRemoval(listener, task.id))
            .setNegativeButton(context.getString(R.string.remove_cancela),
                null).show()
    }

    private class handleRemoval(val listener: OnTaskListFragmentInteractionListener, val taskId: Int) : DialogInterface.OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            listener.onDeleteClick(taskId)
        }
    }
}