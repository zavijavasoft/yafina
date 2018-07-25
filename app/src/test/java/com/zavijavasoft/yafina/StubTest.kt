package com.zavijavasoft.yafina

import com.zavijavasoft.yafina.stub.StubCurrencyMonitorImpl
import com.zavijavasoft.yafina.stub.StubCurrencyStorageImpl
import com.zavijavasoft.yafina.stub.StubTransactionStorageImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class StubTest {

    @Test
    fun testStubStorage() {
        val storage = StubTransactionStorageImpl()
        assertEquals(storage.transactions.size, 13)
    }

    @Test
    fun testStubCurrencyMonitor() {

        val storage = StubCurrencyStorageImpl()
        assertEquals(storage.getCurrencyList().size, 2)

        val monitor = StubCurrencyMonitorImpl(storage)

        assertEquals(monitor.pull().size, 2)



    }
}
