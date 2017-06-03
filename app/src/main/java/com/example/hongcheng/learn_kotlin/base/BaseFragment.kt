package com.example.hongcheng.learn_kotlin.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hongcheng.common.util.RxUtils
import rx.subscriptions.CompositeSubscription

/**
 * Created by hongcheng on 17/5/29.
 */
abstract class BaseFragment : Fragment(){

    protected var mContext : Context ? = null
    protected var rootView : View? = null
    protected var mSubscriptions : CompositeSubscription ? = CompositeSubscription()

    abstract fun getLayoutResId() : Int
    abstract fun initViewModel(savedInstanceState: Bundle?)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
        mSubscriptions = RxUtils.getCompositeSubscription(mSubscriptions)
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
        RxUtils.unsubscribe(mSubscriptions)
        mSubscriptions = null
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater?.inflate(getLayoutResId(), container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel(savedInstanceState)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (hidden) {
            onPause()
        } else {
            onResume()
        }
    }
}