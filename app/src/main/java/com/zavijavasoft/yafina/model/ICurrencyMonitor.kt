package com.zavijavasoft.yafina.model


interface ICurrencyMonitor {
    var currencyStorage: ICurrencyStorage
    fun pull(): List<CurrencyExchangeRatio>
}



