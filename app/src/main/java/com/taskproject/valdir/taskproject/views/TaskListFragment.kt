package com.taskproject.valdir.taskproject.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.taskproject.valdir.taskproject.R
import com.taskproject.valdir.taskproject.adapter.TaskListAdapter
import com.taskproject.valdir.taskproject.business.TaskBusiness
import com.taskproject.valdir.taskproject.constants.TaskConstants
import com.taskproject.valdir.taskproject.entities.OnTaskListFragmentInteractionListener
import com.taskproject.valdir.taskproject.utils.SecurityPreferences


class TaskListFragment : Fragment(), View.OnClickListener {

    //framente necessita ter contexto explicito

    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView
    private lateinit var mTaskBusiness: TaskBusiness //busca os elementos
    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mListener: OnTaskListFragmentInteractionListener
    private var mTaskFilter: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null){
            mTaskFilter = arguments!!.getInt(TaskConstants.TASKFILTER.KEY)
        }

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


        //Classe anonima OnTaskListFragment
        mListener = object : OnTaskListFragmentInteractionListener{
            override fun onListClick(taskId: Int) {


                val bundle: Bundle = Bundle()
                bundle.putInt(TaskConstants.BUNDLE.TASKID, taskId)

                val intent = Intent(mContext, TaskFormActivity::class.java)
                intent.putExtras(bundle)

                startActivity(intent)


            }

        }


        // 1 - Obter o elemento
        mRecyclerTaskList = rootView.findViewById(R.id.recyclerTaskList)

        // 2 - Defini um adapter com os itens de listagem
        //passado para o metodo resume só a instancia da linha abaixo que é feita com uma lista fazia, evita chamada do banco de dados e processamento desnecessário
        mRecyclerTaskList.adapter = TaskListAdapter(mutableListOf(), mListener)

        /* mock para teste
        for(i in 0..50){
            taskList.add(taskList[0].copy(description = "Descrição $i"))
        } */

        // 3 - definir um layout
        mRecyclerTaskList.layoutManager =  LinearLayoutManager(mContext)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    private fun loadTasks() {
        mRecyclerTaskList.adapter = TaskListAdapter(mTaskBusiness.getList(mTaskFilter), mListener)
    }

    companion object {
        @JvmStatic
        fun newInstance(taskfilter: Int): TaskListFragment{
            val args: Bundle = Bundle()
            args.putInt(TaskConstants.TASKFILTER.KEY, taskfilter)

            val fragment = TaskListFragment()
            fragment.arguments = args

           return fragment
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
