package com.zavijavasoft.yafina.model

import com.zavijavasoft.yafina.data.room.CurrencyEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class EntityTest {

    @Test
    fun test_AccountEntityInitialization() {
        val account = AccountEntity(1, "RUR", "R", "")

        assertEquals(1, account.id)
        assertEquals("RUR", account.currency)
        assertEquals("R", account.name)
        assertEquals("", account.description)
    }

    @Test
    fun test_CurrencyEntityInitialization() {
        val actual = CurrencyEntity("RUR")

        assertEquals("RUR", actual.name)
    }

    @Test
    fun test_ArticleTemplateEntityInitialization() {
        val actual = ArticleTemplateEntity(1, 1, true, "def comment")

        assertEquals(1, actual.id)
        assertEquals(1, actual.articleId)
        assertEquals(true, actual.isScheduled)
        assertEquals("def comment", actual.defaultComment)
    }

    @Test
    fun test_BalanceEntityInitialization() {
        val actual = BalanceEntity(hashMapOf("RUR" to 23f, "USD" to 13f), Date(100000))

        assertEquals(hashMapOf("RUR" to 23f, "USD" to 13f), actual.balance)
        assertEquals(Date(100000), actual.lastUpdated)
    }

    @Test
    fun test_balanceEntityToCurrenciesString() {
        val balance = BalanceEntity(hashMapOf("RUR" to 23f, "USD" to 13f), Date(100000))

        val expected = "RUR;USD;"
        val actual = balance.toCurrenciesString()

        assertEquals(expected, actual)
    }

    @Test
    fun test_balanceEntityToValuesString() {
        val balance = BalanceEntity(hashMapOf("RUR" to 23f, "USD" to 13f), Date(100000))

        val expected = "23.0;13.0;"
        val actual = balance.toValuesString()

        assertEquals(expected, actual)
    }
}