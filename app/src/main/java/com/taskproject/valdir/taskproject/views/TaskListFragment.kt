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

class TaskListFragment : Fragment(), View.OnClickListener {

    //framente necessita ter contexto explicito

    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView

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

        // 1 - obter o elemento
        mRecyclerTaskList = rootView.findViewById(R.id.recyclerTaskList)

        // 2 - definir Adapter com itens de listagem
        mRecyclerTaskList.adapter = TaskListAdapter()

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
