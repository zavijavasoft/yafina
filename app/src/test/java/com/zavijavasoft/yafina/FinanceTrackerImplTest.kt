package com.zavijavasoft.yafina

import com.zavijavasoft.yafina.model.FinanceTracker
import com.zavijavasoft.yafina.model.FinanceTrackerImpl
import com.zavijavasoft.yafina.stub.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.util.logging.Logger

class FinanceTrackerImplTest {

    companion object {
        val logger = Logger.getAnonymousLogger()
    }

    val currencyStorage = StubCurrencyStorageImpl()
    val transactionsStorage = StubTransactionStorageImpl()
    val currencyMonitor = StubCurrencyMonitorImpl(currencyStorage)
    val balanceStorage = StubBalanceStorageImpl()
    val accountsStorage = StubAccountsStorageImpl()
    val articlesStorage = StubArticlesStorageImpl()

    val tracker: FinanceTracker = FinanceTrackerImpl(transactionsStorage, balanceStorage, articlesStorage, accountsStorage)


    @Test
    fun testRxBalanceGetter() {
        tracker.retrieveTransactions().blockingGet()
        tracker.currencyRatios = currencyMonitor.ratios
        tracker.calculateTotalBalance().subscribe {
            logger.info("${it.balance["USD"]} USD, ${it.balance["RUR"]} RUR")
        }
    }


    @Test
    fun testRxRestsGetter() {
        val map = tracker.getRests().blockingGet()
        assertEquals(6, map.keys.size)
        assertEquals(0f, map[6])
    }

    @Test
    fun testBalanceInDollarsFirst() {
        tracker.currencyRatios = currencyMonitor.ratios

        tracker.retrieveTransactions().blockingGet()
        tracker.listCurrenciesInAccounts().blockingGet()
        val tr1 = tracker.transactions[0]
        val rur1 = tracker.calculateBalance("RUR", listOf(tr1))
        assertEquals(rur1, 10000.0f)
        val usd1 = tracker.calculateBalance("USD", listOf(tr1))
        assertEquals(usd1, 157.58f)

        val tr2 = tracker.transactions[1]
        val rur2 = tracker.calculateBalance("RUR", listOf(tr1, tr2))
        assertEquals(rur2, 0f)
        val usd2 = tracker.calculateBalance("USD", listOf(tr1, tr2))
        val a = BigDecimal(0)
        val b = BigDecimal(usd2.toDouble())
        val ar = a.setScale(2, BigDecimal.ROUND_HALF_EVEN)
        val br = b.setScale(2, BigDecimal.ROUND_HALF_EVEN)
        assertEquals(ar, br)


        val tr3 = tracker.transactions[2]
        val rur3 = tracker.calculateBalance("RUR", listOf(tr1, tr2, tr3))
        assertEquals(rur3, -10000f)
        val usd3 = tracker.calculateBalance("USD", listOf(tr1, tr2, tr3))
        val a1 = BigDecimal((-10000f / 63.46f).toString())
        val b1 = BigDecimal(usd3.toDouble())
        val ar1 = a1.setScale(2, BigDecimal.ROUND_HALF_EVEN)
        val br1 = b1.setScale(2, BigDecimal.ROUND_HALF_EVEN)
        assertEquals(ar1, br1)


        /*       val result = tracker.calculateTotalBalance()
               assertEquals(result.size, 2)
               assertEquals(result["USD"], 216.18f)
               assertEquals(result["RUR"], 13718.2f)
       */
    }
}