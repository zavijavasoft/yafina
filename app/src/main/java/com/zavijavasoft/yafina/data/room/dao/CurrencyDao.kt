package com.zavijavasoft.yafina.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.zavijavasoft.yafina.data.room.CurrencyEntity

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency")
    fun getCurrencies(): List<CurrencyEntity>

    @Insert
    fun insertCurrency(currency: CurrencyEntity)

    @Delete
    fun removeCurrency(currency: CurrencyEntity)
}