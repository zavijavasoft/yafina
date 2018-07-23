package com.zavijavasoft.yafina.stub

import android.support.annotation.NonNull
import com.zavijavasoft.yafina.model.CurrencyExchangeRatio
import com.zavijavasoft.yafina.model.CurrencyMonitor
import com.zavijavasoft.yafina.model.CurrencyStorage
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton


class StubCurrencyStorage : CurrencyStorage {
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


class StubCurrencyMonitor(override var currencyStorage: CurrencyStorage) : CurrencyMonitor {


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


@Module
class StubCurrencyStorageModule {

    @Singleton
    @Provides
    @NonNull
    fun getCurrencyStorage(): CurrencyStorage {
        return StubCurrencyStorage()
    }

}


@Module
class StubCurrencyMonitorModule {

    @Singleton
    @Provides
    @NonNull
    fun getCurrencyMonitor(currencyStorage: CurrencyStorage): CurrencyMonitor {
        return StubCurrencyMonitor(currencyStorage)
    }

}