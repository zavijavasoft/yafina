package com.zavijavasoft.yafina.ui.balance

import com.arellomobile.mvp.MvpView
import com.zavijavasoft.yafina.model.BalanceChunk

interface BalanceView : MvpView {
    fun update(balances: List<BalanceChunk>)
}