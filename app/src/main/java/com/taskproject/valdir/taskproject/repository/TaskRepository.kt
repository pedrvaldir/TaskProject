package com.taskproject.valdir.taskproject.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.taskproject.valdir.taskproject.constants.DataBaseConstants
import com.taskproject.valdir.taskproject.constants.TaskConstants
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

    //get, update, insert e delete  - CRUD
    fun get(id: Int): TaskEntity? {

        var taskEntity: TaskEntity? = null

        try {
            val cursor: Cursor

            val db = mTaskDataBaseHelper.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.TASK.COLUMNS.USERID,
                DataBaseConstants.TASK.COLUMNS.DESCRIPTION,
                DataBaseConstants.TASK.COLUMNS.PRIORITYID,
                DataBaseConstants.TASK.COLUMNS.DUEDATE,
                DataBaseConstants.TASK.COLUMNS.COMPLETE
            )
            //Selection o Id = ? -> coringa
            val selection =
                "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            // convertido para string, pois selectoinARgs espera Arraylist
            val selectionArgs = arrayOf(id.toString())

            //outra forma de consultar dados :         db.rawQuery("select * from user where email = gabriel", null)
            //Retorno de query cursor
            cursor = db.query(DataBaseConstants.TASK.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

            if (cursor.count > 0) {
                //mover para primeiro registro
                cursor.moveToFirst()

                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.USERID))
                val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.PRIORITYID))
                val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DESCRIPTION))
                val dueData = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.DUEDATE))
                // dessa forma se o valor de retorno for igual a 1 complete será true
                val complete = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMNS.COMPLETE)) == 1)

                //preencho a entidade task
                taskEntity = TaskEntity(id, userId, priorityId, description, dueData, complete)
            }

            cursor.close()
        } catch (e: Exception) {
            return taskEntity
        }
        return taskEntity
    }

    fun getList(userId: Int, taskFilter: Int): MutableList<TaskEntity> {

        val list = mutableListOf<TaskEntity>()

        try {
            val cursor: Cursor
            val db = mTaskDataBaseHelper.readableDatabase

            cursor = db.rawQuery(
                "SELECT * FROM ${DataBaseConstants.TASK.TABLE_NAME} " +
                        "WHERE ${DataBaseConstants.TASK.COLUMNS.USERID} = $userId " +
                        "AND ${DataBaseConstants.TASK.COLUMNS.COMPLETE} = $taskFilter", null
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

    fun insert(task: TaskEntity) {

        try {
            val db = mTaskDataBaseHelper.writableDatabase

            // se por acaso for true assuma o valor 1
            val complete: Int = if (task.complete) 1 else 0

            val insertValues = ContentValues()
            insertValues.put(DataBaseConstants.TASK.COLUMNS.USERID, task.userId)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.PRIORITYID, task.priorityId)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.DUEDATE, task.dueData)
            insertValues.put(DataBaseConstants.TASK.COLUMNS.COMPLETE, complete)

            db.insert(DataBaseConstants.TASK.TABLE_NAME, null, insertValues)
        } catch (e: Exception) {
            throw  e
        }

    }

    fun update(task: TaskEntity) {

        try {
            val db = mTaskDataBaseHelper.writableDatabase

            // se por acaso for true assuma o valor 1
            val complete: Int = if (task.complete) 1 else 0

            val updateValues = ContentValues()
            updateValues.put(DataBaseConstants.TASK.COLUMNS.USERID, task.userId)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.PRIORITYID, task.priorityId)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.DESCRIPTION, task.description)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.DUEDATE, task.dueData)
            updateValues.put(DataBaseConstants.TASK.COLUMNS.COMPLETE, complete)

            val selection =
                "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            // convertido para string, pois selectoinARgs espera Arraylist
            val selectionArgs = arrayOf(task.id.toString())

            db.update(DataBaseConstants.TASK.TABLE_NAME, updateValues, selection, selectionArgs)
        } catch (e: Exception) {
            throw e
        }
    }

    fun delete(id: Int) {
        try {
            val db = mTaskDataBaseHelper.writableDatabase

            val where =
                "${DataBaseConstants.TASK.COLUMNS.ID} = ?"
            // convertido para string, pois selectoinARgs espera Arraylist
            val whereArgs = arrayOf(id.toString())

            db.delete(DataBaseConstants.TASK.TABLE_NAME, where, whereArgs)
        } catch (e: Exception) {
            throw e
        }
    }

}