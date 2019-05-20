package com.taskproject.valdir.taskproject.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.taskproject.valdir.taskproject.R
import com.taskproject.valdir.taskproject.utils.ValidationException
import com.taskproject.valdir.taskproject.business.UserBusiness
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setListeners()

        // Instancia variáveis da classe
        mUserBusiness = UserBusiness(this)

    }

    private fun setListeners() {
        btnSave.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnSave -> {
                handleSavel()
            }
        }

    }

    private fun handleSavel() {
        try {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            //faz a inserção do usuário
            mUserBusiness.insert(name, email, password)

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        } catch (e: ValidationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.erro_inesperado), Toast.LENGTH_LONG).show()
        }
    }
}
