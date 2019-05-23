package com.taskproject.valdir.taskproject.business

import android.content.Context
import com.taskproject.valdir.taskproject.entities.TaskEntity
import com.taskproject.valdir.taskproject.repository.TaskRepository

class TaskBusiness (context: Context){

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)

    //faz a listagem das tarefas de acordo com o usuário
    fun getList(userId: Int) : MutableList<TaskEntity> = mTaskRepository.getList(userId)

    fun insert(taskEntity: TaskEntity) = mTaskRepository.insert(taskEntity)
}