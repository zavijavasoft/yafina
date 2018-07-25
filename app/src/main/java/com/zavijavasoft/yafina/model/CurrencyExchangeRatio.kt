package com.zavijavasoft.yafina.model

import java.util.*

data class CurrencyExchangeRatio(
        val currencyFrom: String,
        val currencyTo: String,
        val ratio: Float,
        val lastUpdated: Date
)