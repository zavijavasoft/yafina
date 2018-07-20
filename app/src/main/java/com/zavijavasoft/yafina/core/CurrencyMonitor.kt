package com.zavijavasoft.yafina.core

import java.util.*

data class CurrencyExchangeRatio(
        val currencyFrom: String,
        val currencyTo: String,
        val ratio: Float,
        val lastUpdated: Date
)


interface CurrencyMonitor {
    fun update()
    fun setCurrencyStorage(currencyStorage: CurrencyStorage?)
    fun getCurrencyStorage(): CurrencyStorage?
    fun registerListener(listener: (List<CurrencyExchangeRatio>) -> Unit)
    fun unregisterListener(listener: (List<CurrencyExchangeRatio>) -> Unit)
    fun pull(): List<CurrencyExchangeRatio>
}


abstract class AbstractCurrencyMonitor : CurrencyMonitor {

    abstract val ratios: List<CurrencyExchangeRatio>

    private var currencyStorage_: CurrencyStorage? = null
    val listenersCount
        get() = listeners.size

    val listeners = mutableListOf<(List<CurrencyExchangeRatio>) -> Unit>()
    override fun setCurrencyStorage(currencyStorage: CurrencyStorage?) {
        currencyStorage_ = currencyStorage
    }

    override fun getCurrencyStorage(): CurrencyStorage? {
        return currencyStorage_
    }

    override fun registerListener(listener: (List<CurrencyExchangeRatio>) -> Unit) {
        for (lis in listeners) {
            if (lis == listener)
                return
        }
        listeners.add(listener)
        listener(ratios)
    }

    override fun unregisterListener(listener: (List<CurrencyExchangeRatio>) -> Unit) {
        for (lis in listeners) {
            if (lis == listener) {
                listeners.remove(listener)
                return
            }
        }
    }

}
