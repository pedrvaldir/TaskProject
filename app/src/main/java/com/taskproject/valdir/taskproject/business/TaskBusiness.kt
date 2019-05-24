package com.taskproject.valdir.taskproject.business

import android.content.Context
import com.taskproject.valdir.taskproject.constants.TaskConstants
import com.taskproject.valdir.taskproject.entities.TaskEntity
import com.taskproject.valdir.taskproject.repository.TaskRepository
import com.taskproject.valdir.taskproject.utils.SecurityPreferences

class TaskBusiness (context: Context){

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    //faz a listagem das tarefas de acordo com o usuário
    fun getList() : MutableList<TaskEntity> {
        val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()
        return mTaskRepository.getList(userId)
    }

    fun insert(taskEntity: TaskEntity) = mTaskRepository.insert(taskEntity)
}