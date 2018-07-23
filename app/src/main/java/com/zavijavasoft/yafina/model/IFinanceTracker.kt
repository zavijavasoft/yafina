package com.zavijavasoft.yafina.model

interface IFinanceTracker {
    val transactions: List<TransactionInfo>
    var currencyRatios: List<CurrencyExchangeRatio>
    fun addTransaction(transaction: TransactionInfo)
    fun removeTransaction(transactionId: Long)
    fun updateTransaction(transaction: TransactionInfo)
    fun retrieveTransactions()
    fun retrieveTransactions(filter: (TransactionInfo) -> Boolean): List<TransactionInfo>
    fun calculateTotalBalance(): Map<String, Float>
    fun calculateBalance(currency: String, trans: List<TransactionInfo>): Float
    fun listCurrenciesInAccounts(): List<String>

}