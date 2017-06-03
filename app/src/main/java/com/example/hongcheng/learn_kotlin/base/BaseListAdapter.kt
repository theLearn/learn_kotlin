package com.example.hongcheng.learn_kotlin.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.example.hongcheng.common.constant.BaseConstants

/**
 * Created by hongcheng on 17/5/29.
 */
open class BaseListAdapter<T, K : RecyclerView.ViewHolder>() : RecyclerView.Adapter<K>(){
    var data = arrayListOf<T>()

    constructor(data : List<T>) : this()
    {
        this.data.clear()
        this.data.addAll(data)
    }

    fun addItem(t : T, isHead : Boolean = false)
    {
        if(isHead) data.add(0, t) else data.add(t)
        removeExceedItem(isHead)
    }

    fun addItem(list : List<T>, isHead : Boolean = false)
    {
        if(isHead) data.addAll(0, list) else data.addAll(list)
        removeExceedItem(isHead)
    }

    /**
     * 根据recyclerview的上限数移除超出的item
     * @param isHead
     */
    private fun removeExceedItem(isHead: Boolean) {
        if (BaseConstants.IS_LIMIT) {
            val exceedCount = data.size - BaseConstants.LIMIT_NUM
            if (exceedCount > 0) {
                for (i in 0..exceedCount - 1) {
                    val removeCount = if (isHead) data.size - 1 else 0
                    data.removeAt(removeCount)
                }
            }
        }
    }

    fun clear()
    {
        this.data.clear()
    }

    fun getItem(position: Int): T = data[position]

    override fun getItemCount(): Int = data.size
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): K? = null
    override fun onBindViewHolder(holder: K, position: Int) {}
}