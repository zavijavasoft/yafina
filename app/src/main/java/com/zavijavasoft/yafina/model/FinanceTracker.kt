package com.zavijavasoft.yafina.model

import io.reactivex.Flowable
import io.reactivex.Single

interface FinanceTracker {
    val transactions: List<TransactionInfo>
    var currencyRatios: List<CurrencyExchangeRatio>
    fun addTransaction(transaction: TransactionInfo)
    fun removeTransaction(transactionId: Long)
    fun updateTransaction(transaction: TransactionInfo)
    fun retrieveTransactions(): Single<List<TransactionInfo>>
    fun retrieveTransactions(filter: (TransactionInfo) -> Boolean): List<TransactionInfo>
    fun calculateTotalBalance(): Flowable<BalanceEntity>
    fun calculateBalance(currency: String, transactionsList: List<TransactionInfo>): Float
    fun listCurrenciesInAccounts(): Single<List<String>>

    fun getArticlesList(): Single<List<ArticleEntity>>
    fun getAccountsList(): Single<List<AccountEntity>>
    fun getRests(): Single<Map<Long, Float>>
}