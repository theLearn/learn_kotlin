package com.example.hongcheng.learn_kotlin.base

import android.Manifest
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.LayoutRes
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.example.hongcheng.common.util.RxUtils
import com.example.hongcheng.learn_kotlin.R
import com.example.hongcheng.learn_kotlin.views.BaseDialog
import rx.subscriptions.CompositeSubscription
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.example.hongcheng.common.CrashHandler
import com.example.hongcheng.common.constant.BaseConstants
import com.example.hongcheng.common.util.FileUtils
import com.example.hongcheng.common.util.StringUtils
import de.mindpipe.android.logging.log4j.LogConfigurator
import org.apache.log4j.Level
import java.io.File


/**
 * Created by hongcheng on 17/5/29.
 */
abstract class BaseActivity : AppCompatActivity(){
    private val REQUEST_EXTERNAL_STORAGE = 1
    val PERMISSIONS_STORAGE = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    var binding: ViewDataBinding? = null

    var isNeedBind = false

    var actionBar: ActionBar? = null

    var mLoadingDialog: Dialog? = null

    var mSubscriptions: CompositeSubscription? = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        BaseApplication.addActivity(this)
        mSubscriptions = RxUtils.getCompositeSubscription(mSubscriptions)
    }

    fun setContentView(@LayoutRes layoutResID: Int, isNeedBind: Boolean) {
        this.isNeedBind = isNeedBind

        if (isNeedBind) {
            binding = DataBindingUtil.setContentView(this, layoutResID)
            initToolBar()
            initViewModel()
        } else {
            setContentView(layoutResID)
        }
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        if (!isNeedBind) {
            initToolBar()
            initViewModel()
        }
    }

    override fun onStart() {
        super.onStart()
        verifyPermissions()
    }

    open fun initToolBar() {

    }

    fun setAbTitle(resId: Int) {
        actionBar?.setTitle(resId)
    }

    fun setAbTitle(title: String) {
        actionBar?.setTitle(title)
    }

    abstract fun initViewModel()

    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
    }

    fun operateLoadingDialog(isOpen: Boolean) {
        if (mLoadingDialog == null) {
            mLoadingDialog = BaseDialog.createLoading(this)
        }

        if (isOpen) {
            mLoadingDialog?.show()
        } else {
            mLoadingDialog?.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RxUtils.unsubscribe(mSubscriptions)
        mSubscriptions = null
        mLoadingDialog = null
        BaseApplication.getRefWatcher()?.watch(this)
        BaseApplication.removeActivity(this)
    }

    fun verifyPermissions() {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            CrashHandler.getInstance().init(this)
            initLog4j()
        }
    }

    fun initLog4j()
    {
        val logConfigurator : LogConfigurator = LogConfigurator()
        var logPath = FileUtils.getLogFilePath()
        try {
            if (!StringUtils.isEmpty(logPath)) {
                var file = File(logPath)
                if (!file.exists()) {
                    file.mkdirs()
                }
                logPath += File.separator + BaseConstants.LOG_FILE
                file = File(logPath)
                if (!file.exists()) {
                    file.createNewFile()
                }
                logConfigurator.fileName = logPath
            }
            val level = if (BaseConstants.DEBUG) Level.ALL else Level.ERROR
            logConfigurator.rootLevel = level
            logConfigurator.setLevel("org.apache", Level.ALL)
            logConfigurator.configure()
        } catch (e: Exception) {
            android.util.Log.e("Application", e.message, e)
        }
    }
}