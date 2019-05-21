package com.taskproject.valdir.taskproject.business

import android.content.Context
import com.taskproject.valdir.taskproject.entities.PriorityEntity
import com.taskproject.valdir.taskproject.repository.PriorityRepository

class PriorityBusiness (context: Context){
    
    private val mPriorityRepository: PriorityRepository = PriorityRepository.getInstance(context)

    fun getList(): MutableList<PriorityEntity> = mPriorityRepository.getList()
}