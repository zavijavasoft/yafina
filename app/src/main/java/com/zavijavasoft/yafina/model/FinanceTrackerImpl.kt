package com.zavijavasoft.yafina.model

import com.zavijavasoft.yafina.utils.roundSum


class FinanceTrackerImpl(var storage: TransactionStorage) : FinanceTracker {

    private var _transactions: List<TransactionInfo> = listOf()

    override val transactions: List<TransactionInfo>
        get() {
            return _transactions
        }

    override var currencyRatios: List<CurrencyExchangeRatio> = listOf()

    override fun addTransaction(transaction: TransactionInfo) {
        _transactions = storage.add(transaction)
    }

    override fun removeTransaction(transactionId: Long) {
        _transactions = storage.remove(transactionId)
    }

    override fun updateTransaction(transaction: TransactionInfo) {
        _transactions = storage.update(transaction)
    }

    override fun retrieveTransactions(filter: (TransactionInfo) -> Boolean): List<TransactionInfo> {
        _transactions = storage.findAll()
        return transactions.filter(filter)
    }

    override fun retrieveTransactions() {
        _transactions = storage.findAll()
    }


    override fun calculateTotalBalance(): Map<String, Float> {
        val listCur = currencyRatios.map { it -> it.currencyFrom }.distinct()
        val map: MutableMap<String, Float> = mutableMapOf()
        for (cur in listCur) {
            val saldo = calculateBalance(cur, transactions)
            map[cur] = saldo
        }
        return map.toMap()
    }


    override fun calculateBalance(currency: String, transactionsList: List<TransactionInfo>): Float {
        val conv = currencyRatios.filter { it -> it.currencyTo == currency }
        var sum = 0f
        for (t in transactionsList) {
            val ratio = if (t.currency != currency) {
                val exchange = conv.find { it -> it.currencyFrom == t.currency }
                        ?: throw IllegalStateException()
                exchange.ratio
            } else 1.0f

            val sign = if (t.type == TransactionType.OUTCOME) -1 else 1
            val payment = t.sum * ratio * sign

            sum += payment.roundSum()

        }

        return sum.roundSum()
    }

    override fun listCurrenciesInAccounts(): List<String> {
        return transactions.asSequence().map { it.currency }.distinct().toList()
    }

}