package com.taskproject.valdir.taskproject.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.taskproject.valdir.taskproject.R

class TaskListFragment : Fragment(), View.OnClickListener {

    //framente necessita ter contexto explicito

    private lateinit var mContext: Context

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
