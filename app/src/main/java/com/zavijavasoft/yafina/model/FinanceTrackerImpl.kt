package com.zavijavasoft.yafina.model

import com.zavijavasoft.yafina.utils.roundSum
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject


class FinanceTrackerImpl @Inject constructor(private val transactionsStorage: TransactionStorage,
                                             private val balanceStorage: BalanceStorage,
                                             private val articlesStorage: ArticlesStorage,
                                             private val accountsStorage: AccountsStorage) : FinanceTracker {

    private var _transactions: List<TransactionInfo> = listOf()

    override val transactions: List<TransactionInfo>
        get() {
            return _transactions
        }

    override var currencyRatios: List<CurrencyExchangeRatio> = listOf()

    val articles = mutableMapOf<Long, ArticleEntity>()
    val accounts = mutableMapOf<Long, AccountEntity>()

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

    override fun retrieveTransactions(): Single<List<TransactionInfo>> {
        _transactions = transactionsStorage.findAll()
        return Single.just(_transactions)
    }


    override fun calculateTotalBalance(): Flowable<BalanceEntity> {


        val initialBalance = balanceStorage.getBalance()
        val calculatedBalance: Single<BalanceEntity> = Single.fromCallable<BalanceEntity> {
            val newBalance = calculateAll()
            balanceStorage.setBalance(newBalance)
            newBalance
        }


        return initialBalance.concatWith(calculatedBalance)
    }

    private fun calculateAll(): BalanceEntity {
        updateArticlesAndAccounts()
        val listCur = currencyRatios.map { it -> it.currencyFrom }.distinct()
        val exchangeList = currencyRatios.filter { it -> it.currencyFrom == "RUR" }
        val map = mutableMapOf<String, Float>()
        val saldo = calculateBalance("RUR", transactions)
        map["RUR"] = saldo
        for (cur in listCur) {
            if (cur == "RUR") continue
            val exchange = exchangeList.find { it -> it.currencyTo == cur }
            if (exchange != null) {
                val payment = saldo * exchange.ratio
                map[cur] = payment.roundSum()
            }
        }

        return BalanceEntity(map.toMap(), Date())
    }


    override fun calculateBalance(currency: String, transactionsList: List<TransactionInfo>): Float {
        val conv = currencyRatios.filter { it -> it.currencyTo == currency }

        var sum = 0f
        for (t in transactionsList) {
            val account = accounts[t.accountId]!!
            val article = articles[t.article]!!
            val ratio = if (account.currency != currency) {
                val exchange = conv.find { it -> it.currencyFrom == account.currency }
                        ?: throw IllegalStateException()
                exchange.ratio
            } else 1.0f

            val sign = if (article.type == ArticleType.OUTCOME) -1 else 1
            val payment = t.sum * ratio * sign

            sum += payment.roundSum()

        }

        return sum.roundSum()
    }

    fun updateArticlesAndAccounts() {
        val accountlist = accountsStorage.getAccounts().blockingGet()
        for (account in accountlist) {
            accounts[account.id] = account
        }

        val articlelist = articlesStorage.getArticles().blockingGet()
        for (article in articlelist) {
            articles[article.articleId] = article
        }
    }

    override fun listCurrenciesInAccounts(): Single<List<String>> {
        updateArticlesAndAccounts()
        return Single.just(accounts.values.asSequence().map { it.currency }.distinct().toList())
    }

    override fun getArticlesList(): Single<List<ArticleEntity>> {
        return articlesStorage.getArticles()
    }

    override fun getAccountsList(): Single<List<AccountEntity>> {
        return accountsStorage.getAccounts()
    }

    override fun getRests(): Single<Map<Long, Float>> {

        return Single.fromCallable<Map<Long, Float>> {

            retrieveTransactions()
            updateArticlesAndAccounts()

            val map = mutableMapOf<Long, Float>()

            for (id in accounts.keys) {
                val sums = _transactions.asSequence()
                        .filter { it -> it.accountId == id }
                        .map { it.sum * if (articles[it.article]?.type == ArticleType.INCOME) 1 else -1 }
                        .toList()
                map[id] = sums.sum().roundSum()
            }
            /*lambda return*/map
        }
    }
}