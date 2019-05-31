package com.taskproject.valdir.taskproject.views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import com.taskproject.valdir.taskproject.R
import com.taskproject.valdir.taskproject.business.PriorityBusiness
import com.taskproject.valdir.taskproject.business.TaskBusiness
import com.taskproject.valdir.taskproject.constants.TaskConstants
import com.taskproject.valdir.taskproject.repository.PriorityCacheConstants
import com.taskproject.valdir.taskproject.utils.SecurityPreferences
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mPriorityBusiness: PriorityBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // mInstancia variáveis
        mSecurityPreferences = SecurityPreferences(this)
        mPriorityBusiness =  PriorityBusiness(this)


        loadPriorityCache()
        startDefaultFragment()
        formatUserName()
        formatDate()
    }

    private fun formatDate() {
        val c = Calendar.getInstance()


        val days = arrayListOf("Domingo","Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira",
            "Sexta-feira", "Sábado")

        val months =  arrayListOf("Janeiro","Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto",
            "Setembro", "Outubro", "Novembro", "Dezembro")

        val str = "${days[c.get(Calendar.DAY_OF_WEEK) - 1]}, ${c.get(Calendar.DAY_OF_MONTH)} de ${months[c.get(Calendar.MONTH)]}"
        textDateDescription.text = str
    }

    private fun formatUserName() {
        val str =  getString(R.string.ola_toolbar) +
                " ${mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)}!"
        textHello.text = str

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        val header = navigationView.getHeaderView(0)

        val name = header.findViewById<TextView>(R.id.textName)
        val email = header.findViewById<TextView>(R.id.textEmail)

        name.text = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)
        email.text = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_EMAIL)



    }

    private fun startDefaultFragment() {
        val fragment: Fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.COMPLETE)

        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        var fragment: Fragment? = null
        val id = item.itemId

        // Handle navigation view item clicks here.
        when (id) {
            // Handle the camera action
            R.id.nav_done -> fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.COMPLETE)
            R.id.nav_todo -> fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.TODO)
            R.id.nav_logout ->  {
                handleLogout()
                return false
            }
        }

        val fragmentManager =  supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frameContent, fragment!!).commit()
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadPriorityCache(){
        PriorityCacheConstants.setCache(mPriorityBusiness.getList())
    }

    private fun handleLogout() {

        // Apagar dados do usuário
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_ID)
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_NAME)
        mSecurityPreferences.removeStoredString(TaskConstants.KEY.USER_EMAIL)

        startActivity(Intent(this, LoginActivity::class.java))
        finish()

    }
}
