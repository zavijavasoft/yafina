package com.zavijavasoft.yafina.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.zavijavasoft.yafina.data.room.AppDatabase
import com.zavijavasoft.yafina.data.room.ScheduledTransactionEntity
import com.zavijavasoft.yafina.data.room.dao.ScheduledTransactionDao
import com.zavijavasoft.yafina.model.TransactionScheduleTimeUnit
import com.zavijavasoft.yafina.utils.TransactionType
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test

class ScheduledTransactionDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: ScheduledTransactionDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.getScheduledTransactionDao()
    }

    @After
    fun tearDown() {
        db.clearAllTables()
    }

    @Test
    fun test_addTransaction() {
        val expected = ScheduledTransactionEntity(1, TransactionType.INCOME.ordinal,
                100000.0f, TransactionScheduleTimeUnit.WEEK,5, "",
                0, 5)


        dao.insertTransaction(expected)

        val actual = dao.getTransactionById(expected.transactionId)
        TestCase.assertEquals(expected, actual)
    }

    @Test
    fun test_addTransactionList() {
        val expected = listOf(
                ScheduledTransactionEntity(1, TransactionType.INCOME.ordinal,
                        100000.0f, TransactionScheduleTimeUnit.WEEK,5, "",
                        0, 5),
                ScheduledTransactionEntity(2, TransactionType.OUTCOME.ordinal,
                        100000.0f, TransactionScheduleTimeUnit.WEEK,0, "",
                        1, 4),
                ScheduledTransactionEntity(3, TransactionType.TRANSITION.ordinal,
                        100000.0f, TransactionScheduleTimeUnit.WEEK,5, "",
                        2, 0)
        )
        expected.forEach { dao.insertTransaction(it) }

        val actual = dao.getTransactions()
        TestCase.assertEquals(expected, actual)
    }

    @Test
    fun test_deleteTransaction() {
        val transactions = listOf(
                ScheduledTransactionEntity(1, TransactionType.INCOME.ordinal,
                        100000.0f, TransactionScheduleTimeUnit.WEEK,5, "",
                        0, 5),
                ScheduledTransactionEntity(2, TransactionType.OUTCOME.ordinal,
                        100000.0f, TransactionScheduleTimeUnit.WEEK,0, "",
                        1, 4),
                ScheduledTransactionEntity(3, TransactionType.TRANSITION.ordinal,
                        100000.0f, TransactionScheduleTimeUnit.WEEK,5, "",
                        2, 0)
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
                ScheduledTransactionEntity(1, TransactionType.INCOME.ordinal,
                        100000.0f, TransactionScheduleTimeUnit.WEEK,5, "",
                        0, 5),
                ScheduledTransactionEntity(2, TransactionType.OUTCOME.ordinal,
                        100000.0f, TransactionScheduleTimeUnit.WEEK,0, "",
                        1, 4),
                ScheduledTransactionEntity(3, TransactionType.TRANSITION.ordinal,
                        100000.0f, TransactionScheduleTimeUnit.WEEK,5, "",
                        2, 0)
        )
        transactions.forEach { dao.insertTransaction(it) }

        val expected = transactions.last().copy(sum = 1000.0f)
        dao.updateTransaction(expected)

        val actual = dao.getTransactionById(expected.transactionId)
        TestCase.assertEquals(expected, actual)
    }
}