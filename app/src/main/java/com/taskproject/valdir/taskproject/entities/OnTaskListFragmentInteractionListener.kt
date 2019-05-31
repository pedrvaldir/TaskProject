package com.taskproject.valdir.taskproject.entities

interface OnTaskListFragmentInteractionListener {

    fun onListClick(taskId: Int)

    fun onDeleteClick(taskId: Int)

    fun onUncompleteClick(taskId: Int)

    fun onCompleteClick(task: Int)
}