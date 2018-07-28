package com.zavijavasoft.yafina.model

import com.zavijavasoft.yafina.utils.roundSum
import rx.Observable
import rx.Single
import java.util.*
import javax.inject.Inject


class FinanceTrackerImpl @Inject constructor(private val transactionsStorage: TransactionStorage,
                                             private val balanceStorage: BalanceStorage) : FinanceTracker {

    private var _transactions: List<TransactionInfo> = listOf()

    override val transactions: List<TransactionInfo>
        get() {
            return _transactions
        }

    override var currencyRatios: List<CurrencyExchangeRatio> = listOf()

    override fun addTransaction(transaction: TransactionInfo) {
        _transactions = transactionsStorage.add(transaction)
    }

    override fun removeTransaction(transactionId: Long) {
        _transactions = transactionsStorage.remove(transactionId)
    }

    override fun updateTransaction(transaction: TransactionInfo) {
        _transactions = transactionsStorage.update(transaction)
    }

    override fun retrieveTransactions(filter: (TransactionInfo) -> Boolean): List<TransactionInfo> {
        _transactions = transactionsStorage.findAll()
        return transactions.filter(filter)
    }

    override fun retrieveTransactions() {
        _transactions = transactionsStorage.findAll()
    }


    override fun calculateTotalBalance(): Observable<BalanceEntity> {


        val initialBalance = balanceStorage.getBalance()
        val calculatedBalance = Single.fromCallable<BalanceEntity> {
            val newBalance = calculateAll()
            balanceStorage.setBalance(newBalance)
            newBalance
        }

        return initialBalance.concatWith(calculatedBalance)
    }

    private fun calculateAll(): BalanceEntity {
        retrieveTransactions()
        val listCur = currencyRatios.map { it -> it.currencyFrom }.distinct()
        val map = mutableMapOf<String, Float>()
        for (cur in listCur) {
            val saldo = calculateBalance(cur, transactions)
            map[cur] = saldo
        }

        return BalanceEntity(map.toMap(), Date())
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