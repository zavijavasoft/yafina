package com.zavijavasoft.yafina.stub

import com.zavijavasoft.yafina.model.OneTimeTransactionInfo
import com.zavijavasoft.yafina.model.TransactionInfo
import com.zavijavasoft.yafina.model.TransactionStorage
import io.reactivex.Single
import java.util.*

class StubTransactionStorageImpl : TransactionStorage {

    val transactions: List<TransactionInfo>

    init {
        val t1 = OneTimeTransactionInfo(sum = 100000.0f, article = 5, datetime = Date(), accountId = 1)
        Thread.sleep(10)
        val t2 = OneTimeTransactionInfo(sum = 50000.0f, article = 2, datetime = Date(), accountId = 1)
        Thread.sleep(10)
        val t3 = OneTimeTransactionInfo(sum = 120.0f, article = 9, datetime = Date(), accountId = 2)
        Thread.sleep(10)
        val t4 = OneTimeTransactionInfo(sum = 20.0f, article = 1, datetime = Date(), accountId = 2)
        Thread.sleep(10)
        val t5 = OneTimeTransactionInfo(sum = 100.0f, article = 6, datetime = Date(), accountId = 3)
        Thread.sleep(10)
        val t6 = OneTimeTransactionInfo(sum = 3000.0f, article = 4, datetime = Date(), accountId = 1)
        Thread.sleep(10)
        val t7 = OneTimeTransactionInfo(sum = 10.0f, article = 9, datetime = Date(), accountId = 3)
        Thread.sleep(10)
        val t8 = OneTimeTransactionInfo(sum = 100.0f, article = 8, datetime = Date(), accountId = 6)
        Thread.sleep(10)
        val t9 = OneTimeTransactionInfo(sum = 450f, article = 8, datetime = Date(), accountId = 3)
        Thread.sleep(10)
        val t10 = OneTimeTransactionInfo(sum = 12340.0f, article = 8, datetime = Date(), accountId = 1)
        Thread.sleep(10)
        val t11 = OneTimeTransactionInfo(sum = 10.0f, article = 9, datetime = Date(), accountId = 1)
        Thread.sleep(10)
        val t12 = OneTimeTransactionInfo(sum = 10.0f, article = 8, datetime = Date(), accountId = 1)
        Thread.sleep(10)
        val t13 = OneTimeTransactionInfo(sum = 10.0f, article = 9, datetime = Date(), accountId = 2)


        transactions = listOf(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13)
    }

    override fun add(transaction: TransactionInfo): Single<List<TransactionInfo>> {
        return Single.just(transactions)
    }

    override fun remove(transaction: TransactionInfo): Single<List<TransactionInfo>> {
        return Single.just(transactions)
    }

    override fun update(transaction: TransactionInfo): Single<List<TransactionInfo>> {
        return Single.just(transactions)
    }

    override fun findAll(): Single<List<TransactionInfo>> {
        return Single.just(transactions)
    }

}

