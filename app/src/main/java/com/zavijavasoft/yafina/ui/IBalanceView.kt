package com.zavijavasoft.yafina.ui

import com.arellomobile.mvp.MvpView

interface IBalanceView : MvpView {
    fun displayBalance(currency: String, sum: Float)
}