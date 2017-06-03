package com.example.hongcheng.learn_kotlin.ui.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.learn_kotlin.R
import com.example.hongcheng.learn_kotlin.base.BaseListAdapter
import com.example.hongcheng.learn_kotlin.databinding.ItemSmartCardBinding
import com.example.hongcheng.learn_kotlin.ui.adapter.models.AnimationModel
import com.example.hongcheng.learn_kotlin.ui.adapter.viewholders.AnimationCategoryViewHolder

/**
 * Created by hongcheng on 17/6/1.
 */
class AnimationCategotyAdapter : BaseListAdapter<AnimationModel, AnimationCategoryViewHolder>() {

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
    }
}