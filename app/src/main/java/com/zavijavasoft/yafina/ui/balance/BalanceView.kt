package com.zavijavasoft.yafina.ui.balance

import com.arellomobile.mvp.MvpView
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet

interface BalanceView : MvpView {
    fun updateBarCurrencies(data: MutableList<IBarDataSet>)
    fun updateBarSpending(data: MutableList<IBarDataSet>)
}