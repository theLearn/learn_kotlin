package com.example.hongcheng.learn_kotlin.ui.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.learn_kotlin.R
import com.example.hongcheng.learn_kotlin.base.BaseListAdapter
import com.example.hongcheng.learn_kotlin.databinding.ItemListInfoBinding
import com.example.hongcheng.learn_kotlin.ui.adapter.models.AnimationDetailModel
import com.example.hongcheng.learn_kotlin.ui.adapter.viewholders.AnimationDetailViewHolder

/**
 * Created by hongcheng on 17/6/4.
 */
class AnimationDetailAdapter : BaseListAdapter<AnimationDetailModel, AnimationDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AnimationDetailViewHolder? {
        val binding = DataBindingUtil.inflate<ItemListInfoBinding>(LayoutInflater.from(parent?.context), R.layout.item_list_info, parent, false)
        val holder = AnimationDetailViewHolder(binding.root)
        holder.binding = binding
        return holder

    }

    override fun onBindViewHolder(holder: AnimationDetailViewHolder, position: Int) {
        val model : AnimationDetailModel = data[position]
        holder.binding?.viewModel = model
        holder.binding?.executePendingBindings()
    }
}
