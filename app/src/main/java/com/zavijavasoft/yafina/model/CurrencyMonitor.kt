package com.zavijavasoft.yafina.model

import io.reactivex.Single

interface CurrencyMonitor {
    fun pull(): Single<List<CurrencyExchangeRatio>>
}



