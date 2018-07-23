package com.zavijavasoft.yafina

import com.zavijavasoft.yafina.stub.StubCurrencyMonitor
import com.zavijavasoft.yafina.stub.StubCurrencyStorage
import com.zavijavasoft.yafina.stub.StubStorage
import org.junit.Assert.assertEquals
import org.junit.Test

class StubTest {

    @Test
    fun testStubStorage() {
        val storage = StubStorage()
        assertEquals(storage.transactions.size, 13)
    }

    @Test
    fun testStubCurrencyMonitor() {

        val storage = StubCurrencyStorage()
        assertEquals(storage.getCurrencyList().size, 2)

        val monitor = StubCurrencyMonitor(storage)

        assertEquals(monitor.pull().size, 2)



    }
}
