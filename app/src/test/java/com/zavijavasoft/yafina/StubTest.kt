package com.zavijavasoft.yafina

import com.zavijavasoft.yafina.core.CurrencyExchangeRatio
import com.zavijavasoft.yafina.stub.StubCurrencyMonitor
import com.zavijavasoft.yafina.stub.StubCurrencyStorage
import com.zavijavasoft.yafina.stub.StubStorage
import org.junit.Assert.assertEquals
import org.junit.Test

class StubTest {

    var i = 0

    @Test
    fun testStubStorage() {
        val storage = StubStorage()
        assertEquals(storage.transactions.size, 13)
    }

    @Test
    fun testStubCurrencyMonitor() {

        val lis: (List<CurrencyExchangeRatio>) -> Unit = { it ->
            assertEquals(it.size, 2)
            i++
        }

        val storage = StubCurrencyStorage()
        assertEquals(storage.getCurrencyList().size, 2)

        val monitor = StubCurrencyMonitor()
        monitor.setCurrencyStorage(storage)
        assertEquals(monitor.pull().size, 2)

        monitor.registerListener(lis)
        assertEquals(monitor.listenersCount, 1)

        monitor.update()

        monitor.unregisterListener(lis)
        assertEquals(monitor.listenersCount, 0)

        assertEquals(i, 2)

    }
}
