package com.example.hongcheng.learn_kotlin.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import com.example.hongcheng.common.util.LoggerUtils
import com.example.hongcheng.data.ActionException
import com.example.hongcheng.data.BaseSubscriber
import com.example.hongcheng.data.RetrofitClient
import com.example.hongcheng.data.RetrofitManager
import com.example.hongcheng.data.request.CardRetrofit
import com.example.hongcheng.data.response.BaseResponse
import com.example.hongcheng.data.response.models.Card
import com.example.hongcheng.learn_kotlin.R
import com.example.hongcheng.learn_kotlin.base.BaseApplication
import com.example.hongcheng.learn_kotlin.base.BaseFragment
import com.example.hongcheng.learn_kotlin.ui.adapter.AnimationCategotyAdapter
import com.example.hongcheng.learn_kotlin.ui.adapter.models.AnimationModel
import com.example.hongcheng.learn_kotlin.views.SnackbarUtil
import kotlinx.android.synthetic.main.fragment_smart_cards.*

/**
 * Created by hongcheng on 17/5/30.
 */
class AnimationCardFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    val mAdapter = AnimationCategotyAdapter()

    override fun getLayoutResId(): Int = R.layout.fragment_smart_cards

    override fun initViewModel(savedInstanceState: Bundle?) {
        srl_fragment_cards.setProgressBackgroundColorSchemeColor(Color.WHITE)
        srl_fragment_cards.setColorSchemeResources(R.color.colorDefault)
        srl_fragment_cards.setProgressViewOffset(false, 0, TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, resources
                        .displayMetrics).toInt())
        srl_fragment_cards.setOnRefreshListener(this)

        rv_smart_cards.layoutManager = LinearLayoutManager(rv_smart_cards.context)
        rv_smart_cards.itemAnimator = DefaultItemAnimator()
        rv_smart_cards.adapter = mAdapter

        srl_fragment_cards.post {
            srl_fragment_cards.isRefreshing = true
            getData()
        }
    }

    override fun onRefresh() {
        getData()
    }

    private fun getData()
    {
        mSubscriptions?.add(RetrofitClient.getInstance().map<List<Card>>(RetrofitManager.createRetrofit<CardRetrofit>(BaseApplication.getInstance(), CardRetrofit::class.java)
                .listCards(), object : BaseSubscriber<BaseResponse<List<Card>>>(BaseApplication.getInstance()) {
            override fun onError(e: ActionException) {
                SnackbarUtil.show(rootView, e.message)
                srl_fragment_cards.isRefreshing = false

                val data = arrayListOf<AnimationModel>()

                data.add(AnimationModel("我家网络", "http://aa", "可以管理wifi哦", ""))
                data.add(AnimationModel("我家看看", "http://aa", "可以看视频哦", ""))
                data.add(AnimationModel("我家存储", "http://aa", "里面有好东西哦", ""))
                data.add(AnimationModel("能耗管理", "http://aa", "节约用电，人人有责", ""))
                data.add(AnimationModel("家庭安防", "http://aa", "让你的家更安全", ""))
                data.add(AnimationModel("环境监控", "http://aa", "随时感知房间环境的变化，是生活更舒适", ""))

                mAdapter.data = data
                mAdapter.notifyDataSetChanged()
            }

            override fun onBaseNext(cardResponse: BaseResponse<List<Card>>?) {
                srl_fragment_cards.isRefreshing = false

                if (cardResponse == null) {
                    SnackbarUtil.show(rootView, "cardResponse is null")
                    return
                }

                if (cardResponse.isSuccess) {
                    LoggerUtils.error(AnimationCardFragment::class.java!!.getName(), cardResponse.toString())
                    val data = arrayListOf<AnimationModel>()
                    for (card in cardResponse.data) {
                        val model = AnimationModel(card.name, card.imageUrl, card.description, card.type)
                        data.add(model)
                    }

                    mAdapter.data = data
                    mAdapter.notifyDataSetChanged()
                } else {
                    SnackbarUtil.show(rootView, cardResponse.description)
                }
            }
        }))
    }
}