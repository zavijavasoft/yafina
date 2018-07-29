package com.zavijavasoft.yafina.core

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.CurrencyExchangeRatio
import com.zavijavasoft.yafina.model.CurrencyMonitor
import com.zavijavasoft.yafina.model.FinanceTracker
import com.zavijavasoft.yafina.model.TransactionInfo
import com.zavijavasoft.yafina.ui.balance.BalanceView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class BalancePresenterImpl @Inject constructor(private val tracker: FinanceTracker,
                                               private val currencyMonitor: CurrencyMonitor)
    : MvpPresenter<BalanceView>(), BalancePresenter {


    override fun update() {
        val currencyRatios = currencyMonitor.pull()
        val transactions = tracker.retrieveTransactions()
        Single.zip(currencyRatios, transactions,
                BiFunction { ratios: List<CurrencyExchangeRatio>,
                             _: List<TransactionInfo> ->
                    tracker.currencyRatios = ratios
                    tracker.calculateTotalBalance()

                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.subscribe {
                        val currenciesUsed = tracker.listCurrenciesInAccounts().blockingGet()
                        val currenciesToDisplay = it.balance.filter { it.key in currenciesUsed }.map { it.key }
                        for (s in currenciesToDisplay) {
                            viewState.displayBalance(s, it.balance[s] ?: 0f)
                        }
                    }
                }) {}
    }
}