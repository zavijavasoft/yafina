package com.zavijavasoft.yafina.services

import com.zavijavasoft.yafina.data.room.ScheduledTransactionEntity
import com.zavijavasoft.yafina.model.TransactionScheduleTimeUnit
import com.zavijavasoft.yafina.utils.TransactionType
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*

private const val ONE_WEEK = 1000L * 60 * 60 * 24 * 7
private const val ONE_MONTH = 1000L * 60 * 60 * 24 * 28
private const val ONE_YEAR = 1000L * 60 * 60 * 24 * 365

class ScheduledTransactionWorkerTest {

    private val calendar = Calendar.getInstance()

    @Before
    fun setUp() {
        calendar.timeInMillis = 1000000
    }

    @After
    fun tearDown() {
        calendar.timeInMillis = 0
    }

    @Test
    fun test_getDelayWeek() {
        val transaction = provideTransaction(calendar.timeInMillis, TransactionScheduleTimeUnit.WEEK)

        val delay = ScheduledTransactionWorker.getDelay(transaction)

        assertThat(delay, greaterThanOrEqualTo(ONE_WEEK))
    }

    @Test
    fun test_getDelayMonth() {
        val transaction = provideTransaction(calendar.timeInMillis, TransactionScheduleTimeUnit.MONTH)

        val delay = ScheduledTransactionWorker.getDelay(transaction)

        assertThat(delay, greaterThanOrEqualTo(ONE_MONTH))
    }

    @Test
    fun test_getDelayYear() {
        val calendar = Calendar.getInstance()
        val transaction = provideTransaction(calendar.timeInMillis, TransactionScheduleTimeUnit.YEAR)

        val delay = ScheduledTransactionWorker.getDelay(transaction)

        assertThat(delay, greaterThanOrEqualTo(ONE_YEAR))
    }

    private fun provideTransaction(time: Long, period: TransactionScheduleTimeUnit) =
            ScheduledTransactionEntity(time,
                    TransactionType.INCOME.ordinal, 100000.0f,
                    period, 1, "", 0, 5)
}