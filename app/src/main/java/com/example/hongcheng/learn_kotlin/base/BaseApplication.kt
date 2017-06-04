package com.example.hongcheng.learn_kotlin.base

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.example.hongcheng.common.CrashHandler
import com.example.hongcheng.common.constant.BaseConstants
import com.example.hongcheng.common.db.DBCore
import com.example.hongcheng.common.util.FileUtils
import com.example.hongcheng.common.util.ResUtil
import com.example.hongcheng.common.util.StringUtils
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import de.mindpipe.android.logging.log4j.LogConfigurator
import org.apache.log4j.Level
import java.io.File
import java.util.*

/**
 * Created by hongcheng on 17/5/29.
 */
class BaseApplication : Application(){

    companion object
    {
        private val activityList = LinkedList<Activity>()
        private var mRefWatcher : RefWatcher? = null

        @JvmStatic
        fun getInstance() : BaseApplication? = SingleHolder.instance
        fun getRefWatcher() : RefWatcher? = mRefWatcher

        fun addActivity(activity : Activity)
        {
            activityList.add(activity)
        }

        fun removeActivity(activity : Activity)
        {
            activityList.remove(activity)
        }

        fun exit()
        {
            for (activity : Activity in activityList)
            {
                activity.finish()
            }

            System.exit(0)
        }
    }

    private object SingleHolder
    {
        var instance : BaseApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        SingleHolder.instance = this
        mRefWatcher = if (BaseConstants.DEBUG) LeakCanary.install(this) else RefWatcher.DISABLED
        DBCore.init(this)
        ResUtil.init(this)

        val permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            CrashHandler.getInstance().init(this)
            FileUtils.initLog4j()
        }
    }
}