package com.zavijavasoft.yafina.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.zavijavasoft.yafina.data.room.AppDatabase
import com.zavijavasoft.yafina.data.room.CurrencyEntity
import com.zavijavasoft.yafina.data.room.dao.CurrencyDao
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test


class CurrencyDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: CurrencyDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.getCurrencyDao()
    }

    @After
    fun tearDown() {
        db.clearAllTables()
    }

    @Test
    fun test_addCurrencyList() {
        val expected = listOf("USD", "RUR", "KZT", "EUR").map { CurrencyEntity(it) }
        dao.insertCurrencies(*expected.toTypedArray())

        val actual = dao.getCurrencies()
        TestCase.assertEquals(expected, actual)
    }
}