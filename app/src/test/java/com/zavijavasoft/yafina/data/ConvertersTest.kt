package com.zavijavasoft.yafina.data

import com.zavijavasoft.yafina.data.room.Converters
import com.zavijavasoft.yafina.data.room.OneTimeTransactionEntity
import com.zavijavasoft.yafina.data.room.ScheduledTransactionEntity
import com.zavijavasoft.yafina.model.ArticleType
import com.zavijavasoft.yafina.model.OneTimeTransactionInfo
import com.zavijavasoft.yafina.model.ScheduledTransactionInfo
import com.zavijavasoft.yafina.model.TransactionScheduleTimeUnit
import com.zavijavasoft.yafina.utils.TransactionType
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class ConvertersTest {

    @Test
    fun test_toArticleTypeIncome() {
        val ordinal = 0

        val expected = ArticleType.INCOME
        val actual = Converters().toArticleType(ordinal)

        assertEquals(expected, actual)
    }

    @Test
    fun test_toArticleTypeOutcome() {
        val ordinal = 1

        val expected = ArticleType.OUTCOME
        val actual = Converters().toArticleType(ordinal)

        assertEquals(expected, actual)
    }

    @Test(expected = Exception::class)
    fun test_toArticleType_Exception() {
        val ordinal = 5

        Converters().toArticleType(ordinal)

        throw Exception("Must never be called")
    }

    @Test
    fun test_toOrdinalArticleTypeIncome() {
        val articleType = ArticleType.INCOME

        val expected = 0
        val actual = Converters().toOrdinal(articleType)

        assertEquals(expected, actual)
    }

    @Test
    fun test_toOrdinalArticleTypeOutcome() {
        val articleType = ArticleType.OUTCOME

        val expected = 1
        val actual = Converters().toOrdinal(articleType)

        assertEquals(expected, actual)
    }

    @Test
    fun test_toDate() {
        val tm = 10000L

        val expected = Date(tm)
        val actual = Converters().toDate(tm)

        assertEquals(expected, actual)
    }

    @Test
    fun test_toTime() {
        val dt = Date(10000L)

        val expected = dt.time
        val actual = Converters().toTime(dt)

        assertEquals(expected, actual)
    }

    @Test
    fun test_toTransactionScheduleTimeUnitWeek() {
        val ordinal = 0

        val expected = TransactionScheduleTimeUnit.WEEK
        val actual = Converters().toTransactionScheduleTimeUnit(ordinal)

        assertEquals(expected, actual)
    }

    @Test
    fun test_toTransactionScheduleTimeUnitMonth() {
        val ordinal = 1

        val expected = TransactionScheduleTimeUnit.MONTH
        val actual = Converters().toTransactionScheduleTimeUnit(ordinal)

        assertEquals(expected, actual)
    }

    @Test
    fun test_toTransactionScheduleTimeUnitYear() {
        val ordinal = 2

        val expected = TransactionScheduleTimeUnit.YEAR
        val actual = Converters().toTransactionScheduleTimeUnit(ordinal)

        assertEquals(expected, actual)
    }

    @Test
    fun test_toOrdinalTransactionScheduleTimeUnitWeek() {
        val period = TransactionScheduleTimeUnit.WEEK

        val expected = 0
        val actual = Converters().toOrdinal(period)

        assertEquals(expected, actual)
    }

    @Test
    fun test_toOrdinalTransactionScheduleTimeUnitMonth() {
        val period = TransactionScheduleTimeUnit.MONTH

        val expected = 1
        val actual = Converters().toOrdinal(period)

        assertEquals(expected, actual)
    }

    @Test
    fun test_toOrdinalTransactionScheduleTimeUnitYear() {
        val period = TransactionScheduleTimeUnit.YEAR

        val expected = 2
        val actual = Converters().toOrdinal(period)

        assertEquals(expected, actual)
    }

    @Test
    fun test_toOneTimeTransactionEntity() {
        val ti = OneTimeTransactionInfo(TransactionType.INCOME, 10f,
                Date(1000), 1, "", 0, 4)

        val expected = OneTimeTransactionEntity(1000, TransactionType.INCOME.ordinal, 10f,
                1, "", 0, 4)
        val actual = Converters.toOneTimeTransactionEntity(ti)

        assertEquals(expected, actual)
    }

    @Test
    fun test_toOneTimeTransactionInfo() {
        val ti = OneTimeTransactionEntity(1000, TransactionType.INCOME.ordinal, 10f,
                1, "", 0, 4)

        val expected = OneTimeTransactionInfo(TransactionType.INCOME, 10f,
                Date(1000), 1, "", 0, 4)
        val actual = Converters.toTransactionInfo(ti)

        assertEquals(expected, actual)
    }

    @Test
    fun test_toScheduledTimeTransactionEntity() {
        val ti = ScheduledTransactionInfo(TransactionType.INCOME, 10f,
                Date(1000), TransactionScheduleTimeUnit.YEAR, 1,
                "", 0, 4)

        val expected = ScheduledTransactionEntity(1000, TransactionType.INCOME.ordinal, 10f,
                TransactionScheduleTimeUnit.YEAR, 1, "", 0, 4)
        val actual = Converters.toScheduledTransactionEntity(ti)

        assertEquals(expected, actual)
    }

    @Test
    fun test_toScheduledTimeTransactionInfo() {
        val t = ScheduledTransactionEntity(1000, TransactionType.INCOME.ordinal, 10f,
                TransactionScheduleTimeUnit.YEAR, 1, "", 0, 4)

        val expected = ScheduledTransactionInfo(TransactionType.INCOME, 10f,
                Date(1000), TransactionScheduleTimeUnit.YEAR, 1,
                "", 0, 4)
        val actual = Converters.toScheduledTransactionInfo(t)

        assertEquals(expected, actual)
    }
}