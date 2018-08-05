package com.zavijavasoft.yafina.data

import com.zavijavasoft.yafina.data.room.CurrencyEntity
import com.zavijavasoft.yafina.data.room.dao.CurrencyDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class CurrencyStorageImpl
@Inject constructor(private val dao: CurrencyDao)
{
    fun getCurrencyList(): Single<List<String>> {
        return Single.just(dao.getCurrencies().map { it.name })
    }

    fun addCurrency(currency: String): Completable {
        return Completable.fromAction {
            dao.insertCurrency(CurrencyEntity(currency))
        }
    }

    fun removeCurrency(currency: String): Completable {
        return Completable.fromAction {
            dao.removeCurrency(CurrencyEntity(currency))
        }
    }
}