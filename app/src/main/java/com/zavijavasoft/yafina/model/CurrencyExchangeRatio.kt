package com.zavijavasoft.yafina.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "currency_rate")
data class CurrencyExchangeRatio(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        @ColumnInfo(name = "currency_from")
        val currencyFrom: String,
        @ColumnInfo(name = "currency_to")
        val currencyTo: String,
        val ratio: Float,
        @ColumnInfo(name = "request_time")
        val requestTime: Date
)

interface CurrencyExchangeRatioStorage {
    fun getLastUpdatedCurrencies(): List<CurrencyExchangeRatio>
    fun getLastUpdatedCurrency(currency: String): CurrencyExchangeRatio?
    fun insertCurrencyRatios(currencyExchangeRatios: List<CurrencyExchangeRatio>)
    fun insertCurrencyRatio(currencyExchangeRatio: CurrencyExchangeRatio)
}