package com.zavijavasoft.yafina.services

import com.zavijavasoft.yafina.model.CurrencyExchangeRatio
import com.zavijavasoft.yafina.model.CurrencyMonitor
import io.reactivex.Single
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.util.*

class CurrencyMonitorImplTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var currencyMonitor: CurrencyMonitor

    @Test
    fun test_pull() {
        val expected = listOf(
                CurrencyExchangeRatio(1, "RUB", "USD", 66.66f, Date()),
                CurrencyExchangeRatio(2, "USD", "RUB", 33.33f, Date()),
                CurrencyExchangeRatio(3, "EUR", "USD", 1.2f, Date())
        )

        `when`(currencyMonitor.pull()).thenReturn(Single.just(expected))

        val actual = currencyMonitor.pull().blockingGet()

        assertEquals(expected, actual)
    }
}