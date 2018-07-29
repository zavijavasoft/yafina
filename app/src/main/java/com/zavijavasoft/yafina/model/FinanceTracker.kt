package com.zavijavasoft.yafina.model

import rx.Observable
import rx.Single

interface FinanceTracker {
    val transactions: List<TransactionInfo>
    var currencyRatios: List<CurrencyExchangeRatio>
    fun addTransaction(transaction: TransactionInfo)
    fun removeTransaction(transactionId: Long)
    fun updateTransaction(transaction: TransactionInfo)
    fun retrieveTransactions()
    fun retrieveTransactions(filter: (TransactionInfo) -> Boolean): List<TransactionInfo>
    fun calculateTotalBalance(): Observable<BalanceEntity>
    fun calculateBalance(currency: String, transactionsList: List<TransactionInfo>): Float
    fun listCurrenciesInAccounts(): List<String>

    fun getArticlesList(): Single<List<ArticleEntity>>
    fun getAccountsList(): Single<List<AccountEntity>>
    fun getRests(): Single<Map<Long, Float>>
}