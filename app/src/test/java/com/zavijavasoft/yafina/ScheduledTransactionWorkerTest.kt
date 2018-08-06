package com.zavijavasoft.yafina

import androidx.work.Data
import com.zavijavasoft.yafina.data.room.ScheduledTransactionEntity
import com.zavijavasoft.yafina.model.TransactionScheduleTimeUnit
import com.zavijavasoft.yafina.services.ScheduledTransactionWorker
import org.hamcrest.Matchers.greaterThan
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.*

class ScheduledTransactionWorkerTest {

    @Test
    fun test_getData() {
        val transaction = ScheduledTransactionEntity(1, 100000.0f, 5, 1,
                period = TransactionScheduleTimeUnit.WEEK)

        val expected = Data.Builder()
                .putFloat("ARG_SUM", transaction.sum)
                .putLong("ARG_ARTICLE", transaction.article)
                .putLong("ARG_ACCOUNT", transaction.accountId)
                .putInt("ARG_TIME_UNIT", transaction.period.ordinal)
                .build()
        val actual = ScheduledTransactionWorker.getData(transaction)

        assertEquals(expected, actual)
    }

    @Test
    fun test_getDelayWeek() {
        val calendar = Calendar.getInstance()
        val transaction = ScheduledTransactionEntity(calendar.timeInMillis, 100000.0f, 5, 1,
                period = TransactionScheduleTimeUnit.WEEK)

        val delay = ScheduledTransactionWorker.getDelay(transaction)
        val oneWeek = (1000 * 60 * 60 * 24 * 7).toLong()

        assertThat(delay, greaterThan(oneWeek))
    }

    @Test
    fun test_getDelayMonth() {
        val calendar = Calendar.getInstance()
        val transaction = ScheduledTransactionEntity(calendar.timeInMillis, 100000.0f, 5, 1,
                period = TransactionScheduleTimeUnit.MONTH)

        val delay = ScheduledTransactionWorker.getDelay(transaction)
        calendar.add(Calendar.MONTH, 1)
        val oneMonth = (1000 * 60 * 60 * 24 * calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).toLong()

        assertThat(delay, greaterThan(oneMonth))
    }

    @Test
    fun test_getDelayYear() {
        val calendar = Calendar.getInstance()
        val transaction = ScheduledTransactionEntity(calendar.timeInMillis, 100000.0f, 5, 1,
                period = TransactionScheduleTimeUnit.YEAR)

        val delay = ScheduledTransactionWorker.getDelay(transaction)
        calendar.add(Calendar.YEAR, 1)
        val oneMonth = (1000 * 60 * 60 * 24 * calendar.getActualMaximum(Calendar.DAY_OF_YEAR)).toLong()

        assertThat(delay, greaterThan(oneMonth))
    }
}