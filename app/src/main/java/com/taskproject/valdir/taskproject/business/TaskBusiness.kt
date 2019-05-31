package com.taskproject.valdir.taskproject.business

import android.content.Context
import com.taskproject.valdir.taskproject.constants.TaskConstants
import com.taskproject.valdir.taskproject.entities.TaskEntity
import com.taskproject.valdir.taskproject.repository.TaskRepository
import com.taskproject.valdir.taskproject.utils.SecurityPreferences

class TaskBusiness (val context: Context){

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun get(id: Int) = mTaskRepository.get(id)

    //faz a listagem das tarefas de acordo com o usuário
    fun getList(taskFilter: Int) : MutableList<TaskEntity> {
        val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()
        return mTaskRepository.getList(userId, taskFilter)
    }

    fun insert(taskEntity: TaskEntity) = mTaskRepository.insert(taskEntity)


    fun update(taskEntity: TaskEntity) = mTaskRepository.update(taskEntity)

    fun delete(taskId: Int) = mTaskRepository.delete(taskId)

    fun complete(taskId: Int, complete: Boolean){
        val task = mTaskRepository.get(taskId)
        if (task!=null){
            task.complete = complete
            mTaskRepository.update(task)
        }

    }

}