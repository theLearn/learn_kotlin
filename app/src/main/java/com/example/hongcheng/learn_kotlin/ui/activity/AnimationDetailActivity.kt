package com.example.hongcheng.learn_kotlin.ui.activity

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.example.hongcheng.common.util.SafeIntentUtils
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.data.ActionException
import com.example.hongcheng.data.BaseSubscriber
import com.example.hongcheng.data.RetrofitClient
import com.example.hongcheng.data.RetrofitManager
import com.example.hongcheng.data.request.CardRetrofit
import com.example.hongcheng.data.response.BaseResponse
import com.example.hongcheng.data.response.models.CardRecommend
import com.example.hongcheng.learn_kotlin.R
import com.example.hongcheng.learn_kotlin.base.BaseActivity
import com.example.hongcheng.learn_kotlin.base.BaseApplication
import com.example.hongcheng.learn_kotlin.databinding.ActivityAnimationDetailBinding
import com.example.hongcheng.learn_kotlin.ui.adapter.AnimationDetailAdapter
import com.example.hongcheng.learn_kotlin.ui.adapter.models.AnimationDetailModel
import com.example.hongcheng.learn_kotlin.ui.adapter.models.AnimationModel
import com.example.hongcheng.learn_kotlin.views.SnackbarUtil
import com.example.hongcheng.learn_kotlin.views.viewHelper.AppBarStateChangeListener
import kotlinx.android.synthetic.main.activity_animation_detail.*

class AnimationDetailActivity : BaseActivity() {

    val mAdapter = AnimationDetailAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_detail, true)
    }

    override fun initToolBar() {
        super.initToolBar()
        setSupportActionBar(tb_detail)
        tb_detail.setNavigationIcon(R.mipmap.back)
        tb_detail.setNavigationOnClickListener { onBackPressed() }
        appBar_detail.addOnOffsetChangedListener(listener)
    }

    override fun initViewModel() {
        val model : AnimationModel = intent.getParcelableExtra(SafeIntentUtils.INTENT_MODEL)
        (binding as ActivityAnimationDetailBinding).model = model
        rv_detail.layoutManager = LinearLayoutManager(rv_detail.context)
        rv_detail.itemAnimator = DefaultItemAnimator()
        rv_detail.adapter = mAdapter
        getData(model?.type!!)
    }

    fun getData(type: String) {
        mSubscriptions?.add(RetrofitClient.getInstance().map(RetrofitManager.createRetrofit(BaseApplication.getInstance(), CardRetrofit::class.java)
                .getCardDetail(type), object : BaseSubscriber<BaseResponse<List<CardRecommend>>>(BaseApplication.getInstance()) {
            override fun onError(e: ActionException) {
                SnackbarUtil.show(binding?.getRoot(), e.message)
                val data = arrayListOf<AnimationDetailModel>()
                data.add(AnimationDetailModel("http://aa", "从零开始", "20话", "2016-09-15 00:00:00", ""))
                data.add(AnimationDetailModel("http://aa", "龙珠超", "20话", "2016-09-15 00:00:00", ""))
                data.add(AnimationDetailModel("http://aa", "灵能百分百", "20话", "2016-09-15 00:00:00", ""))
                data.add(AnimationDetailModel("http://aa", "驱魔少年", "20话", "2016-09-15 00:00:00", ""))
                data.add(AnimationDetailModel("http://aa", "热诚传说", "20话", "2016-09-15 00:00:00", ""))
                data.add(AnimationDetailModel("http://aa", "弹丸论破", "20话", "2016-09-15 00:00:00", ""))
                data.add(AnimationDetailModel("http://aa", "海贼王", "20话", "2016-09-15 00:00:00", ""))
                data.add(AnimationDetailModel("http://aa", "美食的俘虏", "20话", "2016-09-15 00:00:00", ""))
                data.add(AnimationDetailModel("http://aa", "食戟之灵", "20话", "2016-09-15 00:00:00", ""))
                data.add(AnimationDetailModel("http://aa", "狐妖小红娘", "20话", "2016-09-15 00:00:00", ""))

                mAdapter.data = data
                mAdapter.notifyDataSetChanged()
            }

            override fun onBaseNext(cardDetailResponse: BaseResponse<List<CardRecommend>>?) {
                if (cardDetailResponse == null) {
                    SnackbarUtil.show(binding?.getRoot(), "cardDetailResponse is null")
                    return
                }

                if (cardDetailResponse.isSuccess) {
                    val data = arrayListOf<AnimationDetailModel>()
                    for (item in cardDetailResponse.data) {
                        val model = AnimationDetailModel(item.imageUrl, item.content, item.description, item.date, item.infoId)
                        data.add(model)
                    }
                    mAdapter.data = data
                    mAdapter.notifyDataSetChanged()
                } else {
                    SnackbarUtil.show(binding?.getRoot(), cardDetailResponse.description)
                }
            }
        }))
    }

    private var listener = object : AppBarStateChangeListener(){
        override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
            when(state)
            {
                //展开状态
                State.EXPANDED -> ScreenUtils.setWindowStatusBarColor(this@AnimationDetailActivity, R.color.colorTranslucent)
                //折叠状态
                State.COLLAPSED -> ScreenUtils.setWindowStatusBarColor(this@AnimationDetailActivity, R.color.colorDefault)
                //中间状态
                else -> {
                }
            }
        }
    }

}
