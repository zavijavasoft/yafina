package com.zavijavasoft.yafina.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.zavijavasoft.yafina.data.room.AppDatabase
import com.zavijavasoft.yafina.data.room.OneTimeTransactionEntity
import com.zavijavasoft.yafina.data.room.dao.OneTimeTransactionDao
import com.zavijavasoft.yafina.utils.TransactionType
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test

class OneTimeTransactionDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: OneTimeTransactionDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.getOneTimeTransactionDao()
    }

    @After
    fun tearDown() {
        db.clearAllTables()
    }

    @Test
    fun test_addTransaction() {
        val expected = OneTimeTransactionEntity(1, TransactionType.INCOME.ordinal, 100000.0f,
                1, "", 0, 1)


        dao.insertTransaction(expected)

        val actual = dao.getTransactionById(expected.transactionId)
        TestCase.assertEquals(expected, actual)
    }

    @Test
    fun test_addTransactionList() {
        val expected = listOf(
                OneTimeTransactionEntity(1, TransactionType.INCOME.ordinal, 10000.0f,
                        1, "", 0, 1),
                OneTimeTransactionEntity(2, TransactionType.OUTCOME.ordinal, 1000.0f,
                        0, "", 1, 5),
                OneTimeTransactionEntity(3, TransactionType.TRANSITION.ordinal, 100000.0f,
                        1, "", 2, 0)
        )
        expected.forEach { dao.insertTransaction(it) }

        val actual = dao.getTransactions()
        TestCase.assertEquals(expected, actual)
    }

    @Test
    fun test_deleteTransaction() {
        val transactions = listOf(
                OneTimeTransactionEntity(1, TransactionType.INCOME.ordinal, 10000.0f,
                        1, "", 0, 1),
                OneTimeTransactionEntity(2, TransactionType.OUTCOME.ordinal, 1000.0f,
                        0, "", 1, 5),
                OneTimeTransactionEntity(3, TransactionType.TRANSITION.ordinal, 100000.0f,
                        1, "", 2, 0)
        )
        transactions.forEach { dao.insertTransaction(it) }

        val expected = transactions.subList(0, transactions.size-1)
        dao.deleteTransaction(transactions.last())

        val actual = dao.getTransactions()
        TestCase.assertEquals(expected, actual)
    }

    @Test
    fun test_insertTransaction() {
        val transactions = listOf(
                OneTimeTransactionEntity(1, TransactionType.INCOME.ordinal, 10000.0f,
                        1, "", 0, 1),
                OneTimeTransactionEntity(2, TransactionType.OUTCOME.ordinal, 1000.0f,
                        0, "", 1, 5),
                OneTimeTransactionEntity(3, TransactionType.TRANSITION.ordinal, 100000.0f,
                        1, "", 2, 0)
        )
        transactions.forEach { dao.insertTransaction(it) }

        val expected = transactions.last().copy(sum = 1000.0f)
        dao.updateTransaction(expected)

        val actual = dao.getTransactionById(expected.transactionId)
        TestCase.assertEquals(expected, actual)
    }
}