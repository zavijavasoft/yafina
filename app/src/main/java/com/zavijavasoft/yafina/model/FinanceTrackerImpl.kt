package com.zavijavasoft.yafina.model

import com.zavijavasoft.yafina.utils.roundSum
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
        transactionsStorage.add(transaction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data ->
                    _transactions = data
                }
    }

    override fun removeTransaction(transaction: TransactionInfo) {
        transactionsStorage.remove(transaction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data ->
                    _transactions = data
                }
    }

    override fun updateTransaction(transaction: TransactionInfo) {
        transactionsStorage.update(transaction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { data ->
                    _transactions = data
                }
    }

    override fun retrieveTransactions(filter: (TransactionInfo) -> Boolean): Single<List<TransactionInfo>> {
        return Single.fromCallable {
            _transactions = transactionsStorage.findAll().blockingGet()
            _transactions.filter(filter)
        }
    }

    override fun retrieveTransactions(): Single<List<TransactionInfo>> {
        return transactionsStorage.findAll()
                .doOnSuccess {
                    _transactions = it
                }
    }


    override fun calculateTotalBalance(): Flowable<BalanceEntity> {


        val initialBalance = balanceStorage.getBalance()
        val calculatedBalance: Single<BalanceEntity> = Single.fromCallable<BalanceEntity> {
            val newBalance = calculateAll().blockingGet()
            balanceStorage.setBalance(newBalance).subscribe()
            newBalance
        }


        return initialBalance.concatWith(calculatedBalance)
    }

    private fun calculateAll(): Single<BalanceEntity> {
        return Single.fromCallable {
            updateArticlesAndAccounts().blockingAwait()
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
            BalanceEntity(map.toMap(), Date())
        }
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

    fun updateArticlesAndAccounts(): Completable {
        val updateArticles = Completable.fromAction {
            val articlelist = articlesStorage.getArticles().blockingGet()
            for (article in articlelist) {
                articles[article.articleId] = article
            }
        }
        val updateAccounts = Completable.fromAction {
            val accountlist = accountsStorage.getAccounts().blockingGet()
            for (account in accountlist) {
                accounts[account.id] = account
            }
        }
        return updateArticles.concatWith(updateAccounts).subscribeOn(Schedulers.io())
    }

    override fun listCurrenciesInAccounts(): Single<List<String>> {
        return Single.fromCallable {
            updateArticlesAndAccounts().blockingAwait()
            accounts.values.asSequence().map { it.currency }.distinct().toList()
        }
    }

    override fun getArticlesList(): Single<List<ArticleEntity>> {
        return articlesStorage.getArticles()
    }

    override fun getAccountsList(): Single<List<AccountEntity>> {
        return accountsStorage.getAccounts()
    }

    override fun getRests(): Single<Map<Long, Float>> {

        return Single.fromCallable<Map<Long, Float>> {

            _transactions = retrieveTransactions().blockingGet()
            updateArticlesAndAccounts().blockingAwait()

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