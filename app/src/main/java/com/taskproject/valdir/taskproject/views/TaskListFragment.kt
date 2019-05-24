package com.taskproject.valdir.taskproject.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.taskproject.valdir.taskproject.R
import com.taskproject.valdir.taskproject.adapter.TaskListAdapter
import com.taskproject.valdir.taskproject.business.TaskBusiness
import com.taskproject.valdir.taskproject.constants.TaskConstants
import com.taskproject.valdir.taskproject.utils.SecurityPreferences


class TaskListFragment : Fragment(), View.OnClickListener {

    //framente necessita ter contexto explicito

    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView
    private lateinit var mTaskBusiness: TaskBusiness //busca os elementos
    private lateinit var mSecurityPreferences: SecurityPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  arguments?.let {
            //   mParam1 = it.getString(ARG_PARAM1)
            //   mParam2 = it.getString(ARG_PARAM2)
        // }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView =  inflater.inflate(R.layout.fragment_task_list, container, false)

        rootView.findViewById<FloatingActionButton>(R.id.floatAddTask).setOnClickListener(this)
        mContext = rootView.context

        //buscar os elementos
        mTaskBusiness = TaskBusiness(mContext)
        //buscar id
        mSecurityPreferences = SecurityPreferences(mContext)

        // 1 - Obter o elemento
        mRecyclerTaskList = rootView.findViewById(R.id.recyclerTaskList)

        // 2 - Defini um adapter com os itens de listagem
        val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()
        val taskList = mTaskBusiness.getList(userId)


        // mock para teste
        for(i in 0..50){
            taskList.add(taskList[0].copy(description = "Descrição $i"))
        }

        // 2 - definir Adapter com itens de listagem
        mRecyclerTaskList.adapter = TaskListAdapter(taskList)

        // 3 - definir um layout
        mRecyclerTaskList.layoutManager = LinearLayoutManager(mContext)



        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(): TaskListFragment{
           return TaskListFragment()
        }
        // TaskListFragment().apply {
                //    arguments = Bundle().apply {
                //        putString(ARG_PARAM1, param1)
                //       putString(ARG_PARAM2, param2)
        //  }
        //   }
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.floatAddTask -> {
                //não pode ser utilizado como contexto this, deve ser explicito
                startActivity(Intent(mContext, TaskFormActivity::class.java))
            }
        }
    }

}
