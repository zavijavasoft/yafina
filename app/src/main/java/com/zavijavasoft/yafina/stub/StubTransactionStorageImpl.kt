package com.zavijavasoft.yafina.stub

import com.zavijavasoft.yafina.model.TransactionInfo
import com.zavijavasoft.yafina.model.TransactionStorage
import java.util.*

class StubTransactionStorageImpl : TransactionStorage {

    val transactions: List<TransactionInfo>

    init {
        val t1 = TransactionInfo(sum = 100000.0f, article = 5, datetime = Date(), accountId = 1)
        Thread.sleep(10)
        val t2 = TransactionInfo(sum = 50000.0f, article = 2, datetime = Date(), accountId = 1)
        Thread.sleep(10)
        val t3 = TransactionInfo(sum = 120.0f, article = 9, datetime = Date(), accountId = 2)
        Thread.sleep(10)
        val t4 = TransactionInfo(sum = 20.0f, article = 1, datetime = Date(), accountId = 2)
        Thread.sleep(10)
        val t5 = TransactionInfo(sum = 100.0f, article = 6, datetime = Date(), accountId = 3)
        Thread.sleep(10)
        val t6 = TransactionInfo(sum = 3000.0f, article = 4, datetime = Date(), accountId = 1)
        Thread.sleep(10)
        val t7 = TransactionInfo(sum = 10.0f, article = 9, datetime = Date(), accountId = 3)
        Thread.sleep(10)
        val t8 = TransactionInfo(sum = 100.0f, article = 8, datetime = Date(), accountId = 6)
        Thread.sleep(10)
        val t9 = TransactionInfo(sum = 450f, article = 8, datetime = Date(), accountId = 3)
        Thread.sleep(10)
        val t10 = TransactionInfo(sum = 12340.0f, article = 8, datetime = Date(), accountId = 1)
        Thread.sleep(10)
        val t11 = TransactionInfo(sum = 10.0f, article = 9, datetime = Date(), accountId = 1)
        Thread.sleep(10)
        val t12 = TransactionInfo(sum = 10.0f, article = 8, datetime = Date(), accountId = 1)
        Thread.sleep(10)
        val t13 = TransactionInfo(sum = 10.0f, article = 9, datetime = Date(), accountId = 2)


        transactions = listOf(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13)
    }

    override fun add(transaction: TransactionInfo): List<TransactionInfo> {
        return transactions
    }

    override fun remove(transactionId: Long): List<TransactionInfo> {
        return transactions
    }

    override fun update(transaction: TransactionInfo): List<TransactionInfo> {
        return transactions
    }

    override fun findAll(): List<TransactionInfo> {
        return transactions
    }

}

