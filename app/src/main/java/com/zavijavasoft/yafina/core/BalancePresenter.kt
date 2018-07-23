package com.zavijavasoft.yafina.core

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.model.ICurrencyMonitor
import com.zavijavasoft.yafina.model.IFinanceTracker
import com.zavijavasoft.yafina.ui.IBalanceView
import javax.inject.Inject

@InjectViewState
class BalancePresenter : MvpPresenter<IBalanceView>(), IBalancePresenter {


    @Inject
    lateinit var tracker: IFinanceTracker
    @Inject
    lateinit var currencyMonitor: ICurrencyMonitor


    init {
        YaFinaApplication.component.inject(this)
    }

    override fun update() {
        tracker.currencyRatios = currencyMonitor.pull()
        tracker.retrieveTransactions()
        val currenciesUsed = tracker.listCurrenciesInAccounts()
        val balances = tracker.calculateTotalBalance()
        val currenciesToDisplay = balances.filter { it.key in currenciesUsed }.map { it.key }
        for (s in currenciesToDisplay) {
            viewState.displayBalance(s, balances[s] ?: 0f)
        }
    }
}