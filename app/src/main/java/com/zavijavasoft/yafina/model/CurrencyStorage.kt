package com.zavijavasoft.yafina.model

import io.reactivex.Completable
import io.reactivex.Single

interface CurrencyStorage {
    fun getCurrencyList(): Single<List<String>>
    fun addCurrency(currency: String): Completable
    fun removeCurrency(currency: String): Completable
}

