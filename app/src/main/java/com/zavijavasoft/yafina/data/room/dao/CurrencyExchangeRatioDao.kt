package com.zavijavasoft.yafina.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.zavijavasoft.yafina.model.CurrencyExchangeRatio

@Dao
interface CurrencyExchangeRatioDao {

    @Query("""SELECT id, currency_from, currency_to, ratio, max(request_time) as `request_time`
            FROM currency_rate GROUP BY currency_from
    """)
    fun getLastUpdatedCurrencies(): List<CurrencyExchangeRatio>

    @Query("SELECT * FROM currency_rate WHERE currency_from = :currency ORDER BY request_time LIMIT 1")
    fun getLastUpdatedCurrency(currency: String): CurrencyExchangeRatio?

    @Insert
    fun insertCurrencyRatios(currencyExchangeRatio: List<CurrencyExchangeRatio>)

    @Insert
    fun insertCurrencyRatio(currencyExchangeRatio: CurrencyExchangeRatio)
}