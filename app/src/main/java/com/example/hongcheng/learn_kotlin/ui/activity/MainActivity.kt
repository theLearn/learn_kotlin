package com.example.hongcheng.learn_kotlin.ui.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.hongcheng.learn_kotlin.R
import com.example.hongcheng.learn_kotlin.base.BaseActivity
import com.example.hongcheng.learn_kotlin.ui.adapter.FragmentAdapter
import com.example.hongcheng.learn_kotlin.ui.fragment.AnimationCardFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mAdapter: FragmentAdapter? = null
    private val fragments: MutableList<Fragment> = arrayListOf()
    private val titles: MutableList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initToolBar()
    {
        super.initToolBar()

        setSupportActionBar(tb_main)

        actionBar = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.mipmap.ic_menu)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun initViewModel() {
        nv_main.setNavigationItemSelectedListener(this)
        nv_main.setCheckedItem(0)
        setAbTitle(R.string.home)

        titles.add(getString(R.string.animation_card))
        titles.add(getString(R.string.movie_card))
        titles.add(getString(R.string.promote_card))
        titles.add(getString(R.string.record_card))
        titles.add(getString(R.string.find_card))

        fragments.add(AnimationCardFragment())
        fragments.add(AnimationCardFragment())
        fragments.add(AnimationCardFragment())
        fragments.add(AnimationCardFragment())
        fragments.add(AnimationCardFragment())

        mAdapter = FragmentAdapter(supportFragmentManager)
        changeShows(titles, fragments)
    }

    private fun changeShows(title : String, fragment : Fragment)
    {
        val tempTitles = arrayListOf<String>(title)
        val tempFragments = arrayListOf<Fragment>(fragment)
        changeShows(tempTitles, tempFragments)
    }

    private fun changeShows(titles : List<String>, fragments : List<Fragment>)
    {
        if(titles.isEmpty() || fragments.isEmpty()) return

        vp_main.removeAllViews()
        tabs_main.removeAllTabs()

        for(title : String in titles)
        {
            tabs_main.addTab(tabs_main.newTab().setText(title))
        }

        mAdapter?.mTitles = titles
        mAdapter?.mFragments = fragments
        vp_main.adapter = mAdapter
        if(titles.size > 1) tabs_main.setupWithViewPager(vp_main) else tabs_main.visibility = View.GONE
    }

    override fun onNavigationItemSelected(item: MenuItem?): Boolean {
        if(item == null) return false
        if(item!!.isChecked) return false

        when(item.itemId)
        {
            R.id.nav_main_home -> {
                item.isChecked = true
                setAbTitle(R.string.home)}

            R.id.nav_main_extent -> {
                item.isChecked = true
                setAbTitle(R.string.my_apps)}

            R.id.nav_main_market -> {
                item.isChecked = true
                setAbTitle(R.string.my_shopping)}

            R.id.nav_main_message -> {
                item.isChecked = true
                setAbTitle(R.string.home)}

            R.id.nav_main_search -> {
                item.isChecked = true
                setAbTitle(R.string.home)}

            R.id.nav_main_setting -> {
                item.isChecked = true
                setAbTitle(R.string.home)}

            else -> {
            }
        }

        dl_main.closeDrawers()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_tb_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> dl_main.openDrawer(GravityCompat.START)
            R.id.action_message -> {
            }
            R.id.action_fav -> {
            }
            R.id.action_search -> {
            }
            else -> {
            }
        }
        return true
    }
}
