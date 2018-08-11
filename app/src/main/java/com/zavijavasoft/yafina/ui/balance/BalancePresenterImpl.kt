package com.zavijavasoft.yafina.ui.balance

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.zavijavasoft.yafina.model.*
import com.zavijavasoft.yafina.utils.TransactionType
import com.zavijavasoft.yafina.utils.roundSum
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@InjectViewState
class BalancePresenterImpl @Inject constructor(private val tracker: FinanceTracker,
                                               private val currencyMonitor: CurrencyMonitor,
                                               private val transactionStorage: TransactionStorage
) : MvpPresenter<BalanceView>(), BalancePresenter {

    override fun needUpdatePieCurrenciesBetween(dateFrom: Date, dateTo: Date) {
        Single.zip(
                tracker.getAccountsList(),
                transactionStorage.findAllBetween(dateFrom, dateTo),
                BiFunction { accounts: List<AccountEntity>, transactions: List<TransactionInfo> ->
                    val currencyRatios = currencyMonitor.pull().blockingGet()
                    val transactionsGroupedByAccount = transactions.groupBy {
                        if (it.transactionType == TransactionType.INCOME) {
                            it.accountIdTo
                        } else {
                            it.accountIdFrom
                        }
                    }
                    val sets = mutableListOf<IBarDataSet>()
                    var i = 0
                    for ((accountId, t) in transactionsGroupedByAccount) {
                        var sum = 0f
                        val conv = currencyRatios.filter { it -> it.currencyTo == "RUR" }
                        val account = accounts.find { it.id == accountId }
                        val ratio = conv.find { it -> it.currencyFrom == account?.currency }?.ratio ?: 1.0f
                        for (tr in t) {
                            val sign = if (tr.transactionType == TransactionType.OUTCOME) -1 else 1
                            val payment = tr.sum * ratio * sign
                            sum += payment.roundSum()
                        }
                        sets.add(BarDataSet(mutableListOf(BarEntry(i.toFloat(), sum)), account?.currency ?: "UND"))
                        i += 1
                    }
                    sets
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data ->
                    viewState.updateBarCurrencies(data)
                }
    }

    override fun needUpdateBarSpendingBetween(dateFrom: Date, dateTo: Date) {
        Single.fromCallable {
            val transactionsGroupedByMonth = transactionStorage.findAllBetween(dateFrom, dateTo)
                    .blockingGet().groupBy {
                val calendar = Calendar.getInstance()
                calendar.time = it.datetime
                "${calendar.get(Calendar.MONTH)} ${calendar.get(Calendar.YEAR)}"
            }
            val sets = mutableListOf<IBarDataSet>()
            var i = 0
            for ((month, transactions) in transactionsGroupedByMonth) {
                val total = transactions.sumByDouble { it.sum.toDouble() }.toFloat()
                sets.add(BarDataSet(mutableListOf(BarEntry(i.toFloat(), total)), month))
                i += 1
            }
            sets
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data ->
                    viewState.updateBarSpending(data)
                }
    }
}