package com.zavijavasoft.yafina.model


interface CurrencyMonitor {
    var currencyStorage: CurrencyStorage
    fun pull(): List<CurrencyExchangeRatio>
}



