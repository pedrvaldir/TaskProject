package com.taskproject.valdir.taskproject.repository

import com.taskproject.valdir.taskproject.entities.PriorityEntity

class PriorityCacheConstants private constructor() {

    companion object{

        //Construcão CACHE
        //salvando a lista
        fun setCache(list: List<PriorityEntity>){
            for (item in list) {
                mPriorityCache.put(item.id, item.description)

            }
        }

        //acessando a lista
        fun getPriorityDescription(id: Int): String{
            if (mPriorityCache[id]==null){
                return ""
            }
            return mPriorityCache[id].toString()
        }

        //iniciando Hash
        private val mPriorityCache = hashMapOf<Int, String>()


    }
}