package com.zavijavasoft.yafina.services

import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class CbrApiTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var cbrApi: CbrApi

    @Test
    fun test_getCurrenciesStatus() {
        val expected = CbrDataList("28.07.2018", "Foreign Currency Market",
                listOf(CbrDataItem("R01010", "036", "AUD",
                        "1", "Австралийский доллар", "46,4549")))
        `when`(cbrApi.getCurrenciesStatus()).thenReturn(Single.just(expected))

        val actual = cbrApi.getCurrenciesStatus().blockingGet()
        assertEquals(expected, actual)
        assertEquals("28.07.2018", actual.date)
        assertEquals("Foreign Currency Market", actual.name)
        assertEquals(1, actual.list?.size ?: throw Exception("Test goes wrong"))

        val actualList = actual.list ?: throw Exception()
        val actualItem = actualList[0]

        assertEquals("R01010", actualItem.id)
        assertEquals("036", actualItem.numCode)
        assertEquals("AUD", actualItem.charCode)
        assertEquals("1", actualItem.nominal)
        assertEquals("Австралийский доллар", actualItem.name)
        assertEquals("46,4549", actualItem.value)
    }

}