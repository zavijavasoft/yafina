package com.zavijavasoft.yafina.model


interface CurrencyMonitor {
    fun pull(): List<CurrencyExchangeRatio>
}



