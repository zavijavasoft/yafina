package com.zavijavasoft.yafina.data

import com.zavijavasoft.yafina.data.room.dao.CurrencyExchangeRatioDao
import com.zavijavasoft.yafina.model.CurrencyExchangeRatio
import com.zavijavasoft.yafina.model.CurrencyExchangeRatioStorage
import javax.inject.Inject

class CurrencyExchangeRatioStorageImpl @Inject constructor(
        private val currencyExchangeRatioDao: CurrencyExchangeRatioDao
): CurrencyExchangeRatioStorage {

    override fun getLastUpdatedCurrencies(): List<CurrencyExchangeRatio> {
        return currencyExchangeRatioDao.getLastUpdatedCurrencies()
    }

    override fun getLastUpdatedCurrency(currency: String): CurrencyExchangeRatio? {
        return currencyExchangeRatioDao.getLastUpdatedCurrency(currency)
    }

    override fun insertCurrencyRatio(currencyExchangeRatio: CurrencyExchangeRatio) {
        currencyExchangeRatioDao.insertCurrencyRatio(currencyExchangeRatio)
    }

    override fun insertCurrencyRatios(currencyExchangeRatios: List<CurrencyExchangeRatio>) {
        currencyExchangeRatioDao.insertCurrencyRatios(currencyExchangeRatios)
    }

}