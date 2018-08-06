package com.zavijavasoft.yafina.stub

import com.zavijavasoft.yafina.model.CurrencyExchangeRatio
import com.zavijavasoft.yafina.model.CurrencyMonitor
import com.zavijavasoft.yafina.model.CurrencyStorage
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject


class StubCurrencyStorageImpl : CurrencyStorage {
    override fun getCurrencyList(): Single<List<String>> {
        return Single.just(listOf("USD", "RUR", "KZT", "EUR"))
    }

    override fun addCurrency(currency: String): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeCurrency(currency: String): Completable {
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


    override fun pull(): Single<List<CurrencyExchangeRatio>> {
        return Single.just(ratios)
    }
}


