package com.zavijavasoft.yafina

import com.zavijavasoft.yafina.model.FinanceTrackerImpl
import com.zavijavasoft.yafina.model.IFinanceTracker
import com.zavijavasoft.yafina.stub.StubCurrencyMonitor
import com.zavijavasoft.yafina.stub.StubCurrencyStorage
import com.zavijavasoft.yafina.stub.StubStorage
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class FinanceTrackerImplTest {


    @Test
    fun testBalanceInDollarsFirst() {
        val currencyStorage = StubCurrencyStorage()
        val storage = StubStorage()
        val currencyMonitor = StubCurrencyMonitor(currencyStorage)

        val tracker: IFinanceTracker = FinanceTrackerImpl(storage)
        tracker.currencyRatios = currencyMonitor.ratios

        tracker.retrieveTransactions()
        val tr1 = tracker.transactions[0]
        val rur1 = tracker.calculateBalance("RUR", listOf(tr1))
        assertEquals(rur1, 80000.0f)
        val usd1 = tracker.calculateBalance("USD", listOf(tr1))
        assertEquals(usd1, 1260.64f)

        val tr2 = tracker.transactions[1]
        val rur2 = tracker.calculateBalance("RUR", listOf(tr1, tr2))
        assertEquals(rur2, 30000.0f)
        val usd2 = tracker.calculateBalance("USD", listOf(tr1, tr2))
        val a = BigDecimal(30000.0 / 63.46)
        val b = BigDecimal(usd2.toDouble())
        val ar = a.setScale(2, BigDecimal.ROUND_HALF_EVEN)
        val br = b.setScale(2, BigDecimal.ROUND_HALF_EVEN)
        assertEquals(ar, br)


        val tr3 = tracker.transactions[2]
        val rur3 = tracker.calculateBalance("RUR", listOf(tr1, tr2, tr3))
        assertEquals(rur3, 37615.2f)
        val usd3 = tracker.calculateBalance("USD", listOf(tr1, tr2, tr3))
        val a1 = BigDecimal((37615.2f / 63.46f).toString())
        val b1 = BigDecimal(usd3.toDouble())
        val ar1 = a1.setScale(2, BigDecimal.ROUND_HALF_EVEN)
        val br1 = b1.setScale(2, BigDecimal.ROUND_HALF_EVEN)
        assertEquals(ar1, br1)


        val result = tracker.calculateTotalBalance()
        assertEquals(result.size, 2)
        assertEquals(result["USD"], -98.98f)
        assertEquals(result["RUR"], -6281.8f)

    }
}