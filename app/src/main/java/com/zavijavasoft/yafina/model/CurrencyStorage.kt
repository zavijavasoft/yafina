package com.zavijavasoft.yafina.model

import io.reactivex.Single

interface CurrencyStorage {
    fun getCurrencyList(): Single<List<String>>
}

