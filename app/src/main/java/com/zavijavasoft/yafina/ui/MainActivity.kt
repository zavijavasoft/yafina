package com.zavijavasoft.yafina.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.zavijavasoft.yafina.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_operations -> {
                screenViewPager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_transactions -> {
                screenViewPager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_balance -> {
                screenViewPager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                screenViewPager.currentItem = 3
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                screenViewPager.currentItem = 4
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private val pageChangeListener = object: ViewPager.OnPageChangeListener {

        private var prevMenuItem: MenuItem? = null

        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            if (prevMenuItem != null) {
                (prevMenuItem as MenuItem).isChecked = false
            } else {
                navigation.menu.getItem(0).isChecked = false
            }

            navigation.menu.getItem(position).isChecked = true
            prevMenuItem = navigation.menu.getItem(position)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screenViewPager.adapter = ScreenViewPager(supportFragmentManager)
        screenViewPager.addOnPageChangeListener(pageChangeListener)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
