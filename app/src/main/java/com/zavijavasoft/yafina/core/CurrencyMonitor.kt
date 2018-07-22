package com.zavijavasoft.yafina.core


interface CurrencyMonitor {
    var currencyStorage: CurrencyStorage
    fun pull(): List<CurrencyExchangeRatio>
}



