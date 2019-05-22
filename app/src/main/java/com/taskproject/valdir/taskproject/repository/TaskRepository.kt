package com.taskproject.valdir.taskproject.repository

import android.content.Context
import android.database.Cursor
import com.taskproject.valdir.taskproject.constants.DataBaseConstants
import com.taskproject.valdir.taskproject.entities.TaskEntity

class TaskRepository private constructor(context: Context) {

    private var mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    //singleton - caracteristica principal somente uma instancia por classe
    //se a instancia for nula instancia senão retorna a instancia criada e preenchida
    companion object {
        fun getInstance(context: Context): TaskRepository {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }
            return INSTANCE as TaskRepository
        }

        private var INSTANCE: TaskRepository? = null

    }

    fun getList(userId: Int): MutableList<TaskEntity> {

        val list = mutableListOf<TaskEntity>()

        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            cursor = db.rawQuery(
                "SELECT * FROM ${DataBaseConstants.TASK.TABLE_NAME} " +
                        "WHERE ${DataBaseConstants.TASK.COLUMNS.USERID}", null
            )

            if (cursor.count > 0) {
                //enquanto houver registros no cursor
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.ID))
                    val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.PRIORITYID))
                    val description =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DESCRIPTION))
                    val dueData = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DUEDATE))
                    // dessa forma se o valor de retorno for igual a 1 complete será true
                    val complete = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETE)) == 1)

                    list.add(TaskEntity(id, userId, priorityId, description, dueData, complete))
                }
            }

            cursor.close()

        } catch (e: Exception) {
            return list
        }

        return list

    }
}