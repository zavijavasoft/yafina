package com.zavijavasoft.yafina.model

interface CurrencyStorage {
    fun getCurrencyList(): List<String>
    fun addCurrency(currency: String)
    fun removeCurrency(currency: String)
}

