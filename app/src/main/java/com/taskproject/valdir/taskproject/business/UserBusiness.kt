package com.taskproject.valdir.taskproject.business

import android.content.Context
import com.taskproject.valdir.taskproject.R
import com.taskproject.valdir.taskproject.constants.TaskConstants
import com.taskproject.valdir.taskproject.entities.UserEntity
import com.taskproject.valdir.taskproject.utils.ValidationException
import com.taskproject.valdir.taskproject.repository.UserRepository
import com.taskproject.valdir.taskproject.utils.SecurityPreferences
import java.lang.Exception

class UserBusiness(val context: Context) {

    private val mUserRepository : UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun login(email: String, password: String): Boolean {
        val user: UserEntity? = mUserRepository.get(email, password)
        return if (user != null){
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, user.id.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, user.name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, user.email)

            true
        }else{
            false
        }
    }

    fun insert(name: String, email: String, password: String){

        try {
            if (name == "" || email == "" || password ==""){
                throw ValidationException(context.getString(R.string.informe_campos))
            }

            if(mUserRepository.isEmailExistent(email)){
                throw ValidationException(context.getString(R.string.email_uso))
            }

            val userId = mUserRepository.insert(name, email, password)

            //salvar dados do usuário no shared
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, userId.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, email)

        }catch (e: Exception){
            throw e
        }
    }


}