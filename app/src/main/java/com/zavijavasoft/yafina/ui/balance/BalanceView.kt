package com.zavijavasoft.yafina.ui.balance

import com.arellomobile.mvp.MvpView

interface BalanceView : MvpView {
    fun displayBalance(currency: String, sum: Float)
}