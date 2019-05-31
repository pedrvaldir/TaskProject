package com.taskproject.valdir.taskproject.views

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.taskproject.valdir.taskproject.R
import com.taskproject.valdir.taskproject.business.PriorityBusiness
import com.taskproject.valdir.taskproject.business.TaskBusiness
import com.taskproject.valdir.taskproject.constants.TaskConstants
import com.taskproject.valdir.taskproject.entities.PriorityEntity
import com.taskproject.valdir.taskproject.entities.TaskEntity
import com.taskproject.valdir.taskproject.utils.SecurityPreferences
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_task_form.*
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener{

    private lateinit var mPriorityBusiness: PriorityBusiness
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    private val mSimpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/YYYY")

    private var mLstPriorityEntity: MutableList<PriorityEntity> = mutableListOf()//buscar a lista de prioridades
    private var mLstPriorityId: MutableList<Int> = mutableListOf()//buscar o id de prioridades
    private var mTaskId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mPriorityBusiness = PriorityBusiness(this)
        mTaskBusiness = TaskBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        setListener()
        loadPriorities()
        loadDatafromActivity()
    }

    private fun setListener() {
        buttonDate.setOnClickListener(this)
        buttonSave.setOnClickListener(this)
    }

    private fun loadDatafromActivity(){
        val bundle = intent.extras
        if (bundle!= null){
            mTaskId = bundle.getInt(TaskConstants.BUNDLE.TASKID, 0)


            if (mTaskId != 0){
                val task = mTaskBusiness.get(mTaskId)

                editDescription.setText(task?.description)
                buttonDate.text = task?.dueData
                if (task != null) {
                    checkComplete.isChecked = task.complete
                    spinnerPriority.setSelection(getIndex(task.priorityId))
                }

            }
        }



    }

    private fun getIndex(id: Int): Int{
        var index = 0
        for(i in 0..mLstPriorityEntity.size){
            if (mLstPriorityEntity[i].id == id){
                index = i
                break
            }
        }
        return index
    }

    private fun loadPriorities() {
        mLstPriorityEntity = mPriorityBusiness.getList()

        /*  Pode ser usado dessa forma, mas será feito utilizando colletion
            val lst: MutableList<String> = mutableListOf()
            for(i in 0..lstPrioritiesEntity.count())
                lst.add(lstPrioritiesEntity[i].description)
        */

        val lstPriorities = mLstPriorityEntity.map { it.description }
        mLstPriorityId = mLstPriorityEntity.map{it.id}.toMutableList() //utilizado para pegar o  id da lista de prioridades

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
            }R.id.buttonSave ->{
                handleSave()
        }
    }
    }

    private fun handleSave() {

        try{

            // (1, 2, 3, 4)
            val priorityId = mLstPriorityId[spinnerPriority.selectedItemPosition]
            val complete = checkComplete.isChecked
            val dueData = buttonDate.text.toString()
            val description = editDescription.text.toString()
            val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()

            val taskEntity = TaskEntity(mTaskId, userId, priorityId, description, dueData, complete)

            if (mTaskId == 0 ){
                mTaskBusiness.insert(taskEntity)
                Toast.makeText(this, getString(R.string.tarefa_incluida), Toast.LENGTH_LONG).show()
            }else{
                mTaskBusiness.update(taskEntity)
                Toast.makeText(this, getString(R.string.tarefa_alterada), Toast.LENGTH_LONG).show()

            }


            finish()

        }catch (e: Exception){
            Toast.makeText(this, getString(R.string.erro_inesperado_tentenovamente), Toast.LENGTH_SHORT).show()
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
