package com.zavijavasoft.yafina.core

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.YaFinaApplication
import com.zavijavasoft.yafina.model.CurrencyMonitor
import com.zavijavasoft.yafina.model.FinanceTracker
import com.zavijavasoft.yafina.ui.BalanceView
import javax.inject.Inject

@InjectViewState
class BalancePresenterImpl : MvpPresenter<BalanceView>(), BalancePresenter {


    @Inject
    lateinit var tracker: FinanceTracker
    @Inject
    lateinit var currencyMonitor: CurrencyMonitor


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