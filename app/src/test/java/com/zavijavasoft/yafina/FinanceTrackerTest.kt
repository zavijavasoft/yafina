package com.zavijavasoft.yafina

import com.zavijavasoft.yafina.core.FinanceTracker
import com.zavijavasoft.yafina.stub.StubCurrencyMonitor
import com.zavijavasoft.yafina.stub.StubCurrencyStorage
import com.zavijavasoft.yafina.stub.StubStorage
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class FinanceTrackerTest {


    @Test
    fun testBalanceInDollarsFirst() {
        val currencyStorage = StubCurrencyStorage()
        val storage = StubStorage()
        val currencyMonitor = StubCurrencyMonitor()
        currencyMonitor.setCurrencyStorage(currencyStorage)

        FinanceTracker.storage = storage
        FinanceTracker.currencyMonitor = currencyMonitor
        val tr1 = FinanceTracker.getAllTransactions()[0]
        val rur1 = FinanceTracker.calculateBalance("RUR", listOf(tr1))
        assertEquals(rur1, 80000.0f)
        val usd1 = FinanceTracker.calculateBalance("USD", listOf(tr1))
        assertEquals(usd1, 1260.64f)

        val tr2 = FinanceTracker.getAllTransactions()[1]
        val rur2 = FinanceTracker.calculateBalance("RUR", listOf(tr1, tr2))
        assertEquals(rur2, 30000.0f)
        val usd2 = FinanceTracker.calculateBalance("USD", listOf(tr1, tr2))
        val a = BigDecimal(30000.0 / 63.46)
        val b = BigDecimal(usd2.toDouble())
        val ar = a.setScale(2, BigDecimal.ROUND_HALF_EVEN)
        val br = b.setScale(2, BigDecimal.ROUND_HALF_EVEN)
        assertEquals(ar, br)


        val tr3 = FinanceTracker.getAllTransactions()[2]
        val rur3 = FinanceTracker.calculateBalance("RUR", listOf(tr1, tr2, tr3))
        assertEquals(rur3, 37615.2f)
        val usd3 = FinanceTracker.calculateBalance("USD", listOf(tr1, tr2, tr3))
        val a1 = BigDecimal((37615.2f / 63.46f).toString())
        val b1 = BigDecimal(usd3.toDouble())
        val ar1 = a1.setScale(2, BigDecimal.ROUND_HALF_EVEN)
        val br1 = b1.setScale(2, BigDecimal.ROUND_HALF_EVEN)
        assertEquals(ar1, br1)


        val result = FinanceTracker.calculateTotalBalance()
        assertEquals(result.size, 2)
        assertEquals(result["USD"], -98.98f)
        assertEquals(result["RUR"], -6281.8f)

    }
}