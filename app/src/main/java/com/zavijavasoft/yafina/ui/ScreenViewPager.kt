package com.zavijavasoft.yafina.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.zavijavasoft.yafina.ui.balance.BalanceFragment
import com.zavijavasoft.yafina.ui.operation.OperationFragment
import com.zavijavasoft.yafina.ui.settings.SettingsFragment
import com.zavijavasoft.yafina.ui.transactions.TransactionsFragment


private const val PAGE_COUNT = 5
class ScreenViewPager(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> OperationFragment.getInstance()
        1 -> TransactionsFragment.getInstance()
        2 -> BalanceFragment.getInstance()
        3 -> SettingsFragment.getInstance()
        else -> AboutFragment.getInstance()
    }

    override fun getCount(): Int = PAGE_COUNT


}