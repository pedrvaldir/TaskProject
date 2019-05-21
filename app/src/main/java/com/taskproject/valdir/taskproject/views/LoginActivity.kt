package com.taskproject.valdir.taskproject.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.taskproject.valdir.taskproject.R
import com.taskproject.valdir.taskproject.business.UserBusiness
import com.taskproject.valdir.taskproject.constants.TaskConstants
import com.taskproject.valdir.taskproject.utils.SecurityPreferences
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var  mUserBusiness: UserBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //instanciar variaveis da classe
        mUserBusiness = UserBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)


        setListeners()

        verifyLoggedUser()
    }

    private fun verifyLoggedUser() {
        val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID)
        val name = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)

        //USUário logado
        if (userId !="" && name != ""){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setListeners() {
        btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.btnLogin ->{
                handleLogin()
            }
        }
    }

    private fun handleLogin() {
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()


        if (mUserBusiness.login(email, password)){

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }else{
            Toast.makeText(this, getString(R.string.usuario_senha_incorretos), Toast.LENGTH_LONG).show()
        }

    }


}
