package com.taskproject.valdir.taskproject.entities

interface OnTaskListFragmentInteractionListener {

    fun onListClick(taskId: Int)

    fun onDeleteClick(taskId: Int)
}