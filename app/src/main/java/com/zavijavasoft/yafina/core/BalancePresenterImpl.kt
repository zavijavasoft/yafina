package com.zavijavasoft.yafina.core

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.zavijavasoft.yafina.model.*
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


    override fun needUpdate() {
        val currencyRatios = currencyMonitor.pull()
        val transactions = tracker.retrieveTransactions()
        Single.zip(currencyRatios, transactions,
                BiFunction { ratios: List<CurrencyExchangeRatio>,
                             _: List<TransactionInfo> ->
                    tracker.currencyRatios = ratios
                    tracker.calculateTotalBalance()

                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    it.subscribe {
                        val list = mutableListOf<BalanceChunk>()
                        for (currency in it.balance.keys) {
                            list.add(BalanceChunk(currency = currency, sum = it.balance[currency]
                                    ?: 0.0f, lastUpdated = it.lastUpdated))
                        }
                        viewState.update(list)
                    }
                }
    }
}