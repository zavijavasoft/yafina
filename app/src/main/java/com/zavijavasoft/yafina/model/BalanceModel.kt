package com.zavijavasoft.yafina.model

import rx.Completable
import rx.Single
import java.util.*

data class BalanceEntity(val balance: Map<String, Float>, val lastUpdated: Date) {
    companion object {
        fun getInstance(currencyString: String, valuesString: String, lastUpdated: Date): BalanceEntity {
            val map = mutableMapOf<String, Float>()
            val currencies = currencyString.split(";")
            val values = valuesString.split(";")
            for (i in 0 until currencies.size) {
                if (currencies[i].isNotEmpty()) {
                    map[currencies[i]] = values[i].toFloat()
                }
            }
            return BalanceEntity(map.toMap(), lastUpdated)
        }
    }
}

fun BalanceEntity.toCurrenciesString(): String {
    val sb = StringBuffer()
    for (currency in balance.keys) {
        sb.append(currency)
        sb.append(';')
    }
    return sb.toString()
}

fun BalanceEntity.toValuesString(): String {
    val sb = StringBuffer()
    for (currency in balance.keys) {
        sb.append(balance[currency])
        sb.append(';')
    }
    return sb.toString()
}

interface BalanceStorage {
    fun getBalance(): Single<BalanceEntity>
    fun setBalance(balance: BalanceEntity): Completable
}


