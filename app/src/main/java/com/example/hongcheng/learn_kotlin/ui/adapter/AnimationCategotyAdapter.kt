package com.example.hongcheng.learn_kotlin.ui.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.common.util.SafeIntentUtils
import com.example.hongcheng.learn_kotlin.R
import com.example.hongcheng.learn_kotlin.base.BaseListAdapter
import com.example.hongcheng.learn_kotlin.databinding.ItemSmartCardBinding
import com.example.hongcheng.learn_kotlin.ui.activity.AnimationDetailActivity
import com.example.hongcheng.learn_kotlin.ui.adapter.models.AnimationModel
import com.example.hongcheng.learn_kotlin.ui.adapter.viewholders.AnimationCategoryViewHolder

/**
 * Created by hongcheng on 17/6/1.
 */
class AnimationCategotyAdapter() : BaseListAdapter<AnimationModel, AnimationCategoryViewHolder>() {

    var mContext : Context? = null

    constructor(context : Context) : this()
    {
        this.mContext = context
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AnimationCategoryViewHolder? {
        val binding : ItemSmartCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.context), R.layout.item_smart_card, parent, false);
        val holder : AnimationCategoryViewHolder = AnimationCategoryViewHolder(binding.root)
        holder.binding = binding
        return holder
    }

    override fun onBindViewHolder(holder: AnimationCategoryViewHolder, position: Int) {
        val model : AnimationModel = data[position]
        holder.binding?.viewModel = model
        holder.binding?.executePendingBindings()
        holder.binding?.root?.setOnClickListener {
            val intent = Intent(mContext, AnimationDetailActivity::class.java)
            intent.putExtra(SafeIntentUtils.INTENT_MODEL, model)
            mContext?.startActivity(intent) }
    }
}