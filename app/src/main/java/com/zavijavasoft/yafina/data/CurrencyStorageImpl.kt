package com.zavijavasoft.yafina.data

import com.zavijavasoft.yafina.data.room.CurrencyEntity
import com.zavijavasoft.yafina.data.room.dao.CurrencyDao
import com.zavijavasoft.yafina.model.CurrencyStorage
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject


class CurrencyStorageImpl
@Inject constructor(private val dao: CurrencyDao)
    : CurrencyStorage
{
    override fun getCurrencyList(): Single<List<String>> {
        return Single.fromCallable {
            dao.getCurrencies().map { it.name }
        }
    }

    override fun addCurrency(currency: String): Completable {
        return Completable.fromAction {
            dao.insertCurrency(CurrencyEntity(currency))
        }
    }

    override fun removeCurrency(currency: String): Completable {
        return Completable.fromAction {
            dao.removeCurrency(CurrencyEntity(currency))
        }
    }
}