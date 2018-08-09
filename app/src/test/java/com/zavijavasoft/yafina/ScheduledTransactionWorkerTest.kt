package com.zavijavasoft.yafina

import com.zavijavasoft.yafina.data.room.ScheduledTransactionEntity
import com.zavijavasoft.yafina.model.TransactionScheduleTimeUnit
import com.zavijavasoft.yafina.services.ScheduledTransactionWorker
import com.zavijavasoft.yafina.utils.TransactionType
import org.hamcrest.Matchers.greaterThanOrEqualTo
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.*

class ScheduledTransactionWorkerTest {

    @Test
    fun test_getDelayWeek() {
        val calendar = Calendar.getInstance()
        val transaction = ScheduledTransactionEntity(calendar.timeInMillis,
                TransactionType.INCOME.ordinal, 100000.0f,
                TransactionScheduleTimeUnit.WEEK, 1, "", 0, 5)

        val delay = ScheduledTransactionWorker.getDelay(transaction)
        val oneWeek = (1000 * 60 * 60 * 24 * 7).toLong()

        assertThat(delay, greaterThanOrEqualTo(oneWeek))
    }

    @Test
    fun test_getDelayMonth() {
        val calendar = Calendar.getInstance()
        val transaction = ScheduledTransactionEntity(calendar.timeInMillis,
                TransactionType.INCOME.ordinal, 100000.0f,
                TransactionScheduleTimeUnit.MONTH, 1, "", 0, 5)

        val delay = ScheduledTransactionWorker.getDelay(transaction)
        calendar.add(Calendar.MONTH, 1)
        val oneMonth = (1000 * 60 * 60 * 24 * calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).toLong()

        assertThat(delay, greaterThanOrEqualTo(oneMonth))
    }

    @Test
    fun test_getDelayYear() {
        val calendar = Calendar.getInstance()
        val transaction = ScheduledTransactionEntity(calendar.timeInMillis,
                TransactionType.INCOME.ordinal, 100000.0f,
                TransactionScheduleTimeUnit.YEAR, 1, "", 0, 5)

        val delay = ScheduledTransactionWorker.getDelay(transaction)
        calendar.add(Calendar.YEAR, 1)
        val oneMonth = (1000 * 60 * 60 * 24 * calendar.getActualMaximum(Calendar.DAY_OF_YEAR)).toLong()

        assertThat(delay, greaterThanOrEqualTo(oneMonth))
    }
}