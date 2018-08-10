package com.zavijavasoft.yafina.data

import com.zavijavasoft.yafina.data.room.dao.CurrencyDao
import com.zavijavasoft.yafina.model.CurrencyStorage
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
}