package com.zavijavasoft.yafina.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.zavijavasoft.yafina.data.room.AppDatabase
import com.zavijavasoft.yafina.data.room.dao.AccountDao
import com.zavijavasoft.yafina.model.AccountEntity
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test

class AccountDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: AccountDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.getAccountDao()
    }

    @After
    fun tearDown() {
        db.clearAllTables()
    }

    @Test
    fun test_addAccount() {
        val expected = AccountEntity(1, "RUR", "Наличные в рублях", "Все, что есть по карманам")

        dao.insertAccount(expected)

        val actual = dao.getAccountById(expected.id)
        TestCase.assertEquals(expected, actual)
    }

    @Test
    fun test_addAccountList() {
        val expected = listOf(
                AccountEntity(1, "RUR",
                        "Наличные в рублях", "Все, что есть по карманам"),
                AccountEntity(2, "USD",
                        "Наличные в долларах", "Все, что есть по карманам"),
                AccountEntity(3, "USD",
                        "Дебетовая карта", "Сбербанк VISA"),
                AccountEntity(4, "RUR",
                        "Кредитная карта", "ВТБ Mastercard"),
                AccountEntity(5, "RUR",
                        "Яндекс.Деньги", "Счет на Яндекс.Деньги")
        )
        expected.forEach { dao.insertAccount(it) }

        val actual = dao.getAccounts()
        TestCase.assertEquals(expected, actual)
    }
}