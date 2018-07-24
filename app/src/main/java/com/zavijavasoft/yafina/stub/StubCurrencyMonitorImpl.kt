package com.zavijavasoft.yafina.stub

import com.zavijavasoft.yafina.model.CurrencyExchangeRatio
import com.zavijavasoft.yafina.model.CurrencyMonitor
import com.zavijavasoft.yafina.model.CurrencyStorage
import java.util.*
import javax.inject.Inject


class StubCurrencyStorageImpl : CurrencyStorage {
    override fun getCurrencyList(): List<String> {
        return listOf("USD", "RUR")
    }

    override fun addCurrency(currency: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeCurrency(currency: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


class StubCurrencyMonitorImpl @Inject constructor(val currencyStorage: CurrencyStorage) : CurrencyMonitor {


    val ratios: List<CurrencyExchangeRatio>


    init {
        val usdrur = CurrencyExchangeRatio("USD", "RUR", 63.46f, Date())
        val rurusd = CurrencyExchangeRatio("RUR", "USD", 1.0f / 63.46f, Date())
        ratios = listOf(usdrur, rurusd)
    }


    override fun pull(): List<CurrencyExchangeRatio> {
        return ratios
    }
}


