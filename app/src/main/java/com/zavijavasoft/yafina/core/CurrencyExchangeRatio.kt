package com.zavijavasoft.yafina.core

import java.util.*

data class CurrencyExchangeRatio(
        val currencyFrom: String,
        val currencyTo: String,
        val ratio: Float,
        val lastUpdated: Date
)