package com.taskproject.valdir.taskproject.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import com.taskproject.valdir.taskproject.R
import com.taskproject.valdir.taskproject.business.PriorityBusiness
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener{


    private val mSimpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/YYYY")
    private lateinit var mPriorityBusiness: PriorityBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mPriorityBusiness = PriorityBusiness(this)

        setListener()

        loadPriorities()
    }

    private fun setListener() {
        buttonDate.setOnClickListener(this)
    }

    private fun loadPriorities() {
        val lstPrioritiesEntity = mPriorityBusiness.getList()

        /*  Pode ser usado dessa forma, mas será feito utilizando colletion
            val lst: MutableList<String> = mutableListOf()
            for(i in 0..lstPrioritiesEntity.count())
                lst.add(lstPrioritiesEntity[i].description)
        */

        val lstPriorities = lstPrioritiesEntity.map { it.description }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lstPriorities)

        spinnerPriority.adapter = adapter
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        mSimpleDateFormat.format(calendar.time)

        buttonDate.text = mSimpleDateFormat.format(calendar.time)

    }

    override fun onClick(v: View) {
        when(v.id){
           R.id.buttonDate -> {
            openDatePickerDialog()

        }
        }
    }

    private fun openDatePickerDialog() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, this, year, month, dayOfMonth).show()
    }

}
