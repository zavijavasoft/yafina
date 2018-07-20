package com.zavijavasoft.yafina.stub

import com.zavijavasoft.yafina.core.TransactionInfo
import com.zavijavasoft.yafina.core.TransactionStorage
import com.zavijavasoft.yafina.core.TransactionType
import java.util.*

class StubStorage : TransactionStorage {

    val transactions: List<TransactionInfo>

    init {
        val t1 = TransactionInfo(currency = "RUR", type = TransactionType.INCOME, sum = 80000.0f, article = "Зарплата", datetime = Date())
        Thread.sleep(10)
        val t2 = TransactionInfo(currency = "RUR", type = TransactionType.OUTCOME, sum = 50000.0f, article = "Покупка ноутбука", datetime = Date())
        Thread.sleep(10)
        val t3 = TransactionInfo(currency = "USD", type = TransactionType.INCOME, sum = 120.0f, article = "Гонорар", datetime = Date())
        Thread.sleep(10)
        val t4 = TransactionInfo(currency = "USD", type = TransactionType.OUTCOME, sum = 20.0f, article = "Кафешка", datetime = Date())
        Thread.sleep(10)
        val t5 = TransactionInfo(currency = "USD", type = TransactionType.INCOME, sum = 100.0f, article = "Дивиденды", datetime = Date())
        Thread.sleep(10)
        val t6 = TransactionInfo(currency = "RUR", type = TransactionType.OUTCOME, sum = 3000.0f, article = "ЖКХ", datetime = Date())
        Thread.sleep(10)
        val t7 = TransactionInfo(currency = "USD", type = TransactionType.INCOME, sum = 10.0f, article = "Алименты", datetime = Date())
        Thread.sleep(10)
        val t8 = TransactionInfo(currency = "USD", type = TransactionType.OUTCOME, sum = 100.0f, article = "Хакая-то фигня с AliExpress", datetime = Date())
        Thread.sleep(10)
        val t9 = TransactionInfo(currency = "USD", type = TransactionType.OUTCOME, sum = 450f, article = "Тур куда-то", datetime = Date())
        Thread.sleep(10)
        val t10 = TransactionInfo(currency = "RUR", type = TransactionType.OUTCOME, sum = 12340.0f, article = "Не помню куда", datetime = Date())
        Thread.sleep(10)
        val t11 = TransactionInfo(currency = "RUR", type = TransactionType.INCOME, sum = 10.0f, article = "Продал холодильник", datetime = Date())
        Thread.sleep(10)
        val t12 = TransactionInfo(currency = "RUR", type = TransactionType.OUTCOME, sum = 10.0f, article = "Прочее", datetime = Date())
        Thread.sleep(10)
        val t13 = TransactionInfo(currency = "USD", type = TransactionType.INCOME, sum = 10.0f, article = "Нашел на улице", datetime = Date())


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