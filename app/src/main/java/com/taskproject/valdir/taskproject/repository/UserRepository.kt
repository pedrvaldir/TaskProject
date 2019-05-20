package com.taskproject.valdir.taskproject.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.taskproject.valdir.taskproject.constants.DataBaseConstants
import com.taskproject.valdir.taskproject.entities.UserEntity

class UserRepository private constructor(context: Context) {

    private var mTaskDataBaseHelper: TaskDataBaseHelper = TaskDataBaseHelper(context)

    //singleton - caracteristica principal somente uma instancia por classe
    //se a instancia for nula instancia senão retorna a instancia criada e preenchida
    companion object {
        fun getInstance(context: Context): UserRepository {
            if (INSTANCE == null) {
                INSTANCE = UserRepository(context)
            }
            return INSTANCE as UserRepository
        }

        private var INSTANCE: UserRepository? = null

    }

    fun get(email: String, password: String) : UserEntity?{

        var userEntity: UserEntity? = null

        try {
            val cursor: Cursor

            val db = mTaskDataBaseHelper.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.USER.COLUMNS.ID,
                DataBaseConstants.USER.COLUMNS.NAME,
                DataBaseConstants.USER.COLUMNS.EMAIL,
                DataBaseConstants.USER.COLUMNS.PASSWORD
            )

            val selection =
                "${DataBaseConstants.USER.COLUMNS.EMAIL} = ? AND ${DataBaseConstants.USER.COLUMNS.PASSWORD} = ?"
            val selectionArgs = arrayOf(email, password)

            //outra forma de consultar dados :         db.rawQuery("select * from user where email = gabriel", null)
            //Retorno de query cursor
            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

            if (cursor.count > 0) {
                cursor.moveToFirst()

                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.ID))
                val name =   cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.NAME))
                val email =  cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.EMAIL))


                //preencho a entidade do usuário
                userEntity = UserEntity(userId, name, email)
            }

            cursor.close()
        } catch (e: Exception) {
            return userEntity
        }
        return userEntity
    }

    fun insert(name: String, email: String, password: String): Int {
        //select, update, insert, delete
        val db = mTaskDataBaseHelper.writableDatabase

        val insertValues = ContentValues()
        insertValues.put(DataBaseConstants.USER.COLUMNS.NAME, name)
        insertValues.put(DataBaseConstants.USER.COLUMNS.EMAIL, email)
        insertValues.put(DataBaseConstants.USER.COLUMNS.PASSWORD, password)

        return db.insert(DataBaseConstants.USER.TABLE_NAME, null, insertValues).toInt()

    }


    fun isEmailExistent(email: String): Boolean {

        var ret: Boolean = false
        try {
            //instancia banco de dados
            val cursor: Cursor

            val db = mTaskDataBaseHelper.readableDatabase
            val projection = arrayOf(DataBaseConstants.USER.COLUMNS.ID)

            val selection = "${DataBaseConstants.USER.COLUMNS.EMAIL} = ?"
            val selectionArgs = arrayOf(email)

            //outra forma de consultar dados :         db.rawQuery("select * from user where email = gabriel", null)
            //Retorno de query cursor
            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

            ret = cursor.count > 0

            cursor.close()

        } catch (e: Exception) {
            throw e
        }
        return ret
    }
}