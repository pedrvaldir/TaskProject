package com.taskproject.valdir.taskproject.entities

data class TaskEntity(val id: Int,
                      val userId: Int,
                      val priorityId: Int,
                      var description: String,
                      var dueData: String,
                      var complete: Boolean

                      )