package com.zavijavasoft.yafina.core

import java.math.BigDecimal


object FinanceTracker {

    val push: (List<CurrencyExchangeRatio>) -> Unit = {
        currencyRatios = it
    }

    private var transactions: List<TransactionInfo> = listOf()

    var storage: TransactionStorage? = null

    var currencyMonitor: CurrencyMonitor? = null
        set(value) {
            field = value
            currencyMonitor?.registerListener(push)
        }

    var currencyRatios: List<CurrencyExchangeRatio> = listOf()

    fun addTransaction(transaction: TransactionInfo) {
        transactions = storage?.add(transaction) ?: listOf()
    }

    fun removeTransaction(transactionId: Long) {
        transactions = storage?.remove(transactionId) ?: listOf()
    }

    fun updateTransaction(transaction: TransactionInfo) {
        transactions = storage?.update(transaction) ?: listOf()
    }

    fun getAllTransactions(): List<TransactionInfo> {
        transactions = storage?.findAll() ?: listOf()
        return transactions
    }

    fun getTransactions(filter: (TransactionInfo) -> Boolean): List<TransactionInfo> {
        transactions = storage?.findAll() ?: listOf()
        return transactions.filter(filter)
    }


    fun listAvailableCurrencies(): List<String> {
        return currencyMonitor?.getCurrencyStorage()?.getCurrencyList() ?: listOf()
    }


    fun calculateTotalBalance(): Map<String, Float> {
        val listCur = listAvailableCurrencies()
        val map: MutableMap<String, Float> = mutableMapOf()
        for (cur in listCur) {
            val saldo = calculateBalance(cur, transactions)
            map[cur] = saldo
        }
        return map.toMap()
    }


    fun calculateBalance(currency: String, trans: List<TransactionInfo>): Float {
        val conv = currencyRatios.filter { it -> it.currencyTo == currency }
        var sum = 0f
        for (t in trans) {
            val ratio = if (t.currency != currency) {
                val exchange = conv.find { it -> it.currencyFrom == t.currency }
                        ?: throw IllegalStateException()
                exchange.ratio
            } else 1.0f

            val sign = if (t.type == TransactionType.OUTCOME) -1 else 1
            val payment = roundSum(t.sum * ratio * sign)

            sum += payment

        }

        return roundSum(sum)
    }

    private fun roundSum(value: Float) = BigDecimal(value.toDouble()).setScale(2, BigDecimal.ROUND_HALF_EVEN).toFloat()


}