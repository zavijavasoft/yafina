package com.zavijavasoft.yafina.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.zavijavasoft.yafina.data.room.AppDatabase
import com.zavijavasoft.yafina.data.room.TransactionEntity
import com.zavijavasoft.yafina.data.room.dao.TransactionDao
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test

class TransactionDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: TransactionDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.getTransactionDao()
    }

    @After
    fun tearDown() {
        db.clearAllTables()
    }

    @Test
    fun test_addTransaction() {
        val expected = TransactionEntity(1, 100000.0f, 5, 1)

        dao.insertTransaction(expected)

        val actual = dao.getTransactionById(expected.transactionId)
        TestCase.assertEquals(expected, actual)
    }

    @Test
    fun test_addTransactionList() {
        val expected = listOf(
                TransactionEntity(2,100000.0f, 5, 1),
                TransactionEntity(3,50000.0f, 2, 1),
                TransactionEntity(4,120.0f, 9, 2),
                TransactionEntity(5,20.0f, 2, 1)
        )
        expected.forEach { dao.insertTransaction(it) }

        val actual = dao.getTransactions()
        TestCase.assertEquals(expected, actual)
    }

    @Test
    fun test_deleteTransaction() {
        val transactions = listOf(
                TransactionEntity(2,100000.0f, 5, 1),
                TransactionEntity(3,50000.0f, 2, 1),
                TransactionEntity(4,120.0f, 9, 2),
                TransactionEntity(5,20.0f, 2, 1)
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
                TransactionEntity(2,100000.0f, 5, 1),
                TransactionEntity(3,50000.0f, 2, 1),
                TransactionEntity(4,120.0f, 9, 2),
                TransactionEntity(5,20.0f, 2, 1)
        )
        transactions.forEach { dao.insertTransaction(it) }

        val expected = transactions.last().copy(sum = 1000.0f)
        dao.updateTransaction(expected)

        val actual = dao.getTransactionById(expected.transactionId)
        TestCase.assertEquals(expected, actual)
    }
}